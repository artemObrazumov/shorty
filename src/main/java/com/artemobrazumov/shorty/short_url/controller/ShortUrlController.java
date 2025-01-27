package com.artemobrazumov.shorty.short_url.controller;

import com.artemObrazumov.token.user.TokenUser;
import com.artemobrazumov.shorty.short_url.dto.*;
import com.artemobrazumov.shorty.short_url.dto.redirection_stats.RedirectionCountriesDTO;
import com.artemobrazumov.shorty.short_url.dto.redirection_stats.RedirectionCountDTO;
import com.artemobrazumov.shorty.short_url.dto.redirection_stats.RedirectionRefererDTO;
import com.artemobrazumov.shorty.short_url.service.ShortUrlService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/urls")
public class ShortUrlController {

    private final ShortUrlService shortUrlService;

    public ShortUrlController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @GetMapping
    public ShortUrlListDTO getShortUrlsOfUser(@AuthenticationPrincipal TokenUser user,
                                              @RequestParam(value = "p", required = false, defaultValue = "1") Integer page) {
        return shortUrlService.getUserShortUrls(user, page);
    }

    @GetMapping("/{id}")
    public ShortUrlDetailsDTO getShortUrlDetails(@AuthenticationPrincipal TokenUser user, @PathVariable("id") Long id) {
        return shortUrlService.getShortUrlDetails(user, id);
    }

    @GetMapping("/{id}/redirections")
    public RedirectionsDTO getShortUrlRedirections(@AuthenticationPrincipal TokenUser user, @PathVariable("id") Long id,
                                                   @RequestParam(value = "p", required = false, defaultValue = "1") Integer page) {
        return shortUrlService.getShortUrlRedirections(user, id, page);
    }

    @GetMapping("/{id}/stats/redirection_count")
    public RedirectionCountDTO getRedirectionsCountStats(@AuthenticationPrincipal TokenUser user,
                                                         @PathVariable("id") Long id,
                                                         @RequestParam("from") LocalDateTime from,
                                                         @RequestParam("to") LocalDateTime to) {
        return shortUrlService.getRedirectionsCountStats(user, id, from, to);
    }

    @GetMapping("/{id}/stats/redirection_countries")
    public RedirectionCountriesDTO getRedirectionsCountriesStats(@AuthenticationPrincipal TokenUser user,
                                                                 @PathVariable("id") Long id,
                                                                 @RequestParam("from") LocalDateTime from,
                                                                 @RequestParam("to") LocalDateTime to) {
        return shortUrlService.getRedirectionsCountriesStats(user, id, from, to);
    }

    @GetMapping("/{id}/stats/redirection_referer")
    public RedirectionRefererDTO getRedirectionsRefererStats(@AuthenticationPrincipal TokenUser user,
                                                             @PathVariable("id") Long id,
                                                             @RequestParam("from") LocalDateTime from,
                                                             @RequestParam("to") LocalDateTime to) {
        return shortUrlService.getRedirectionsRefererStats(user, id, from, to);
    }

    @PostMapping
    public ShortUrlResponseDTO createShortUrl(@AuthenticationPrincipal TokenUser user,
                                              @RequestBody @Valid ShortUrlDTO shortUrlDTO) {
        return shortUrlService.createShortUrl(user.getUserId(), shortUrlDTO);
    }
}
