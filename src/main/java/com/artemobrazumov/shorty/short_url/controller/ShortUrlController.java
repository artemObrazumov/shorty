package com.artemobrazumov.shorty.short_url.controller;

import com.artemObrazumov.token.user.TokenUser;
import com.artemobrazumov.shorty.short_url.dto.*;
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

    @GetMapping("/{id}/redirections")
    public RedirectionsDTO getShortUrlRedirections(@AuthenticationPrincipal TokenUser user,
                                                   @PathVariable("id") Long id,
                                                   @RequestParam(value = "p", required = false, defaultValue = "1") Integer page) {
        return shortUrlService.getShortUrlRedirections(user, id, page);
    }

    @PostMapping
    public ShortUrlResponseDTO createShortUrl(@AuthenticationPrincipal TokenUser user,
                                              @RequestBody @Valid ShortUrlDTO shortUrlDTO) {
        return shortUrlService.createShortUrl(user.getUserId(), shortUrlDTO);
    }
}
