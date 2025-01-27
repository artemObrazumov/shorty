package com.artemobrazumov.shorty.short_url.security;

import com.artemObrazumov.token.user.TokenUser;
import com.artemobrazumov.shorty.short_url.entity.ShortUrl;
import com.artemobrazumov.shorty.short_url.service.ShortUrlService;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;

@Component
public class ShortUrlPermissionEvaluator implements PermissionEvaluator {

    private final ShortUrlService shortUrlService;

    public ShortUrlPermissionEvaluator(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (targetDomainObject instanceof ShortUrl shortUrl) {
            var user = (TokenUser) authentication.getPrincipal();
            return Objects.equals(shortUrl.getAuthor().getId(), user.getUserId());
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable id, String type, Object permission) {
        if (ShortUrl.class.getSimpleName().equals(type)) {
            ShortUrl post = shortUrlService.findShortUrlById((Long) id);
            return hasPermission(authentication, post, permission);
        }
        return false;
    }
}
