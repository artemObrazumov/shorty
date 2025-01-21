package com.artemobrazumov.shorty.short_url.controller;

import com.artemObrazumov.token.user.TokenUser;
import com.artemobrazumov.shorty.short_url.dto.ShortUrlDTO;
import com.artemobrazumov.shorty.short_url.dto.ShortUrlDetailsDTO;
import com.artemobrazumov.shorty.short_url.dto.ShortUrlResponseDTO;
import com.artemobrazumov.shorty.short_url.service.ShortUrlService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/urls")
public class ShortUrlController {

    private final ShortUrlService shortUrlService;

    public ShortUrlController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @GetMapping("/{id}")
    public ShortUrlDetailsDTO getShortUrlDetails(@AuthenticationPrincipal TokenUser user,
                                                 @PathVariable("id") Long id) {
        return shortUrlService.getShortUrlDetails(user, id);
    }

    @PostMapping
    public ShortUrlResponseDTO createShortUrl(@AuthenticationPrincipal TokenUser user,
                                              @RequestBody @Valid ShortUrlDTO shortUrlDTO) {
        return shortUrlService.createShortUrl(user.getUserId(), shortUrlDTO);
    }
}
