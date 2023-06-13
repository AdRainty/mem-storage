package com.adrainty.im;

import com.adrainty.im.controller.MemWebSocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

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
        ApplicationContext context = SpringApplication.run(MemImApplication.class, args);
        MemWebSocketServer.setApplicationContext(context);
    }

}
