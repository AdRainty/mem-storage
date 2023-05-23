package com.adrainty.gateway.filter;

import cn.hutool.core.codec.Base64;
import com.adrainty.gateway.utils.TokenUtils;
import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/5/19 2:26
 */
@Configuration
public class GatewayFilter implements GlobalFilter, Ordered {

    private final AuthClient client;

    @Autowired
    private GatewayFilter(AuthClient client) {
        this.client = client;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestUrl = exchange.getRequest().getPath().value();
        AntPathMatcher pathMatcher = new AntPathMatcher();
        if (pathMatcher.match("/oauth/**", requestUrl)) {
            return chain.filter(exchange);
        }
        String token = TokenUtils.getToken(exchange);
        if (StringUtils.isBlank(token)) {
            return TokenUtils.noTokenMono(exchange);
        } else {
            Map<Object, String> accessible = client.accessible(exchange.getRequest(), token);
            //判断用户凭证是否存在
            if (accessible.get("user_name") == null) {
                return TokenUtils.invalidTokenMono(exchange);
            }
            //获取凭证
            Object principal = accessible.get("user_name");
            //获取用户权限
            List<String> authorities = new ArrayList<>();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("principal", principal);
            jsonObject.put("authorities", authorities);
            //转为base64编码
            String base64 = Base64.encode(jsonObject.toJSONString());
            //把base64加入请求头中
            ServerHttpRequest tokenRequest = exchange.getRequest().mutate().header("json-token", base64).build();
            exchange.mutate().request(tokenRequest).build();
            return chain.filter(exchange);

        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
