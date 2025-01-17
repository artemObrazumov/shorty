package com.artemobrazumov.shorty.short_url;

import com.artemobrazumov.shorty.short_url.filter.ShortUrlPasswordFilter;
import com.artemobrazumov.shorty.short_url.service.ShortUrlService;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.csrf.CsrfFilter;

public class ShortUrlConfigurer extends AbstractHttpConfigurer<ShortUrlConfigurer, HttpSecurity> {

    private final ShortUrlService shortUrlService;

    public ShortUrlConfigurer(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @Override
    public void configure(HttpSecurity builder) {
        var shortUrlPasswordFilter = new ShortUrlPasswordFilter(shortUrlService);
        builder.addFilterAfter(shortUrlPasswordFilter, CsrfFilter.class);
    }
}
