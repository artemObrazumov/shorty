package com.artemobrazumov.shorty.short_url.controller;

import com.artemObrazumov.token.user.TokenUser;
import com.artemobrazumov.shorty.short_url.dto.ShortUrlDTO;
import com.artemobrazumov.shorty.short_url.dto.ShortUrlResponseDTO;
import com.artemobrazumov.shorty.short_url.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/urls")
public class ShortUrlController {

    private final ShortUrlService shortUrlService;

    public ShortUrlController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @PostMapping
    public ShortUrlResponseDTO createShortUrl(@AuthenticationPrincipal TokenUser user, @RequestBody ShortUrlDTO shortUrlDTO) {
        return shortUrlService.createShortUrl(user.getUserId(), shortUrlDTO);
    }
}
