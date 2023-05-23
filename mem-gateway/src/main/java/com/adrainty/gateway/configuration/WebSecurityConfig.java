package com.adrainty.gateway.configuration;

import com.adrainty.common.exception.MemException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/5/3 17:53
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig  {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authorize -> {
            try {
                authorize.requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/static/**", "/resources/**").permitAll()
                        .anyRequest().authenticated()
                        .and().csrf(AbstractHttpConfigurer::disable);
            } catch (Exception e) {
                throw new MemException("权限认证失败");
            }
        }).build();
    }
}
