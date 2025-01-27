package com.artemobrazumov.shorty.short_url;

import com.artemobrazumov.shorty.ip_info.service.IpInfoService;
import com.artemobrazumov.shorty.short_url.filter.ShortUrlPasswordFilter;
import com.artemobrazumov.shorty.short_url.service.RedirectionService;
import com.artemobrazumov.shorty.short_url.service.ShortUrlService;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.csrf.CsrfFilter;

public class ShortUrlConfigurer extends AbstractHttpConfigurer<ShortUrlConfigurer, HttpSecurity> {

    private final ShortUrlService shortUrlService;
    private final RedirectionService redirectionService;
    private final IpInfoService ipInfoService;

    public ShortUrlConfigurer(ShortUrlService shortUrlService, RedirectionService redirectionService, IpInfoService ipInfoService) {
        this.shortUrlService = shortUrlService;
        this.redirectionService = redirectionService;
        this.ipInfoService = ipInfoService;
    }

    @Override
    public void init(HttpSecurity builder) throws Exception {
        builder.authorizeHttpRequests(authorizer -> {
            authorizer.requestMatchers("/urls/17/redirections").permitAll();
            authorizer.requestMatchers("/urls/**").authenticated();
            authorizer.requestMatchers("/r/**").permitAll();
            authorizer.requestMatchers("/*/password**").permitAll();
        });
    }

    @Override
    public void configure(HttpSecurity builder) {
        var shortUrlPasswordFilter = new ShortUrlPasswordFilter(shortUrlService, redirectionService, ipInfoService);
        builder.addFilterAfter(shortUrlPasswordFilter, CsrfFilter.class);
    }
}
