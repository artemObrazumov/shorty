package com.artemobrazumov.shorty.short_url;

import com.artemobrazumov.shorty.short_url.filter.ShortUrlPasswordFilter;
import com.artemobrazumov.shorty.short_url.service.RedirectionService;
import com.artemobrazumov.shorty.short_url.service.ShortUrlService;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.csrf.CsrfFilter;

public class ShortUrlConfigurer extends AbstractHttpConfigurer<ShortUrlConfigurer, HttpSecurity> {

    private final ShortUrlService shortUrlService;
    private final RedirectionService redirectionService;

    public ShortUrlConfigurer(ShortUrlService shortUrlService, RedirectionService redirectionService) {
        this.shortUrlService = shortUrlService;
        this.redirectionService = redirectionService;
    }

    @Override
    public void init(HttpSecurity builder) throws Exception {
        builder.authorizeHttpRequests(authorizer -> {
            authorizer.requestMatchers("/urls/**").authenticated();
        });
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        var shortUrlPasswordFilter = new ShortUrlPasswordFilter(shortUrlService, redirectionService);
        builder.addFilterAfter(shortUrlPasswordFilter, CsrfFilter.class);
    }
}
