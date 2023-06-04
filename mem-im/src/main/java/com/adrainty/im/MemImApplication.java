package com.adrainty.im;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/6/3 18:29
 */

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class MemImApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemImApplication.class, args);
    }

}
