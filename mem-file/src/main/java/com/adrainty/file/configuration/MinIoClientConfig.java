package com.adrainty.file.configuration;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/5/27 21:53
 */

@Data
@Component
public class MinIoClientConfig {

    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.port}")
    private Integer port;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;

    @Value("${minio.isSSL:false}")
    private boolean isSSL;

    @Bean
    public MinioClient minioClient() {
        MinioClient.Builder builder = MinioClient.builder();
        if (isSSL) {
            builder.endpoint(endpoint);
        } else {
            builder.endpoint(endpoint, port, false);
        }
        builder.credentials(accessKey, secretKey);
        return builder.build();
    }

}
