package com.adrainty.gateway.filter;

import com.alibaba.fastjson2.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/5/19 2:35
 */
@Component
public class AuthClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthClient.class);

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${checkToken.url}")
    private String checkTokenUrl;

    public Map<Object,String> accessible(ServerHttpRequest request, String token) {
        HttpEntity<?> entity = new HttpEntity<>(request.getHeaders());
        ParameterizedTypeReference<String> myBean = new  ParameterizedTypeReference<>() {};
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(checkTokenUrl + "?token=" + token, HttpMethod.POST, entity, myBean);
            LOGGER.info("oauth request: {}, response body: {}, reponse status: {}",
                    entity, responseEntity.getBody(), responseEntity.getStatusCode());
            String body = responseEntity.getBody();
            return JSON.parseObject(body, Map.class);
        } catch (RestClientException e) {
            LOGGER.error("oauth failed.", e);
        }
        return new HashMap<>();
    }
}
