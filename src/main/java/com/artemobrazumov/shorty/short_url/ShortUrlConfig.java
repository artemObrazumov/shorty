package com.artemobrazumov.shorty.short_url;

import com.artemobrazumov.shorty.ip_info.service.IpInfoService;
import com.artemobrazumov.shorty.short_url.factory.ShortUrlStringGenerator;
import com.artemobrazumov.shorty.short_url.security.ShortUrlPermissionEvaluator;
import com.artemobrazumov.shorty.short_url.service.RedirectionService;
import com.artemobrazumov.shorty.short_url.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Component;

@Configuration
@EnableMethodSecurity
public class ShortUrlConfig {

    @Value("${short-url.length}")
    private int length;

    @Bean
    public ShortUrlStringGenerator urlStringGenerator() {
        return new ShortUrlStringGenerator(length);
    }

    @Bean
    public ShortUrlConfigurer shortUrlConfigurer(
            ShortUrlService shortUrlService, RedirectionService redirectionService, IpInfoService ipInfoService
    ) {
        return new ShortUrlConfigurer(shortUrlService, redirectionService, ipInfoService);
    }

    @Bean
    public MethodSecurityExpressionHandler expressionHandler(ShortUrlPermissionEvaluator shortUrlPermissionEvaluator) {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(shortUrlPermissionEvaluator);
        return expressionHandler;
    }
}
