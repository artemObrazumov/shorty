package com.artemobrazumov.shorty.short_url;

import com.artemobrazumov.shorty.short_url.factory.ShortUrlStringGenerator;
import com.artemobrazumov.shorty.short_url.service.RedirectionService;
import com.artemobrazumov.shorty.short_url.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class ShortUrlConfig {

    @Value("${short-url.length}")
    private int length;

    @Bean
    public ShortUrlStringGenerator urlStringGenerator() {
        return new ShortUrlStringGenerator(length);
    }

    @Bean
    public ShortUrlConfigurer shortUrlConfigurer(
            ShortUrlService shortUrlService, RedirectionService redirectionService
    ) {
        return new ShortUrlConfigurer(shortUrlService, redirectionService);
    }
}
