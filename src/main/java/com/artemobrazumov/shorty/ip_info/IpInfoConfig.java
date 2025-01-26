package com.artemobrazumov.shorty.ip_info;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class IpInfoConfig {

    @Value("${ip-info.base-url}")
    private String BASE_URL;

    @Bean
    public RestClient restClient() {
        return RestClient.create(BASE_URL);
    }
}
