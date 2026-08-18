package com.oneenterprise.orderservice.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
@EnableConfigurationProperties(UserServiceProperties.class)
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder, UserServiceProperties properties) {
        // Timeouts are configured values, not magic numbers — see
        // application.yml (user-service.connect-timeout-ms / read-timeout-ms).
        // A network call to User Service should never hang forever if
        // User Service is slow or unreachable.
        return builder
                .connectTimeout(Duration.ofMillis(properties.getConnectTimeoutMs()))
                .readTimeout(Duration.ofMillis(properties.getReadTimeoutMs()))
                .build();
    }
}
