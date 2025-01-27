package com.artemobrazumov.shorty.short_url.controller;

import com.artemObrazumov.token.user.TokenUser;
import com.artemobrazumov.shorty.short_url.dto.*;
import com.artemobrazumov.shorty.short_url.dto.redirection_stats.RedirectionCountriesDTO;
import com.artemobrazumov.shorty.short_url.dto.redirection_stats.RedirectionCountDTO;
import com.artemobrazumov.shorty.short_url.dto.redirection_stats.RedirectionRefererDTO;
import com.artemobrazumov.shorty.short_url.service.ShortUrlService;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasPermission(#id, 'ShortUrl', 'view_details')")
    @GetMapping("/{id}")
    public ShortUrlDetailsDTO getShortUrlDetails(@PathVariable("id") Long id) {
        return shortUrlService.getShortUrlDetails(id);
    }

    @PreAuthorize("hasPermission(#id, 'ShortUrl', 'view_stats')")
    @GetMapping("/{id}/redirections")
    public RedirectionsDTO getShortUrlRedirections(@PathVariable("id") Long id,
                                                   @RequestParam(value = "p", required = false, defaultValue = "1") Integer page) {
        return shortUrlService.getShortUrlRedirections(id, page);
    }

    @PreAuthorize("hasPermission(#id, 'ShortUrl', 'view_stats')")
    @GetMapping("/{id}/stats/redirection_count")
    public RedirectionCountDTO getRedirectionsCountStats(@PathVariable("id") Long id,
                                                         @RequestParam("from") LocalDateTime from,
                                                         @RequestParam("to") LocalDateTime to) {
        return shortUrlService.getRedirectionsCountStats(id, from, to);
    }

    @PreAuthorize("hasPermission(#id, 'ShortUrl', 'view_stats')")
    @GetMapping("/{id}/stats/redirection_countries")
    public RedirectionCountriesDTO getRedirectionsCountriesStats(@PathVariable("id") Long id,
                                                                 @RequestParam("from") LocalDateTime from,
                                                                 @RequestParam("to") LocalDateTime to) {
        return shortUrlService.getRedirectionsCountriesStats(id, from, to);
    }

    @PreAuthorize("hasPermission(#id, 'ShortUrl', 'view_stats')")
    @GetMapping("/{id}/stats/redirection_referer")
    public RedirectionRefererDTO getRedirectionsRefererStats(@PathVariable("id") Long id,
                                                             @RequestParam("from") LocalDateTime from,
                                                             @RequestParam("to") LocalDateTime to) {
        return shortUrlService.getRedirectionsRefererStats(id, from, to);
    }

    @PostMapping
    public ShortUrlResponseDTO createShortUrl(@AuthenticationPrincipal TokenUser user,
                                              @RequestBody @Valid ShortUrlDTO shortUrlDTO) {
        return shortUrlService.createShortUrl(user.getUserId(), shortUrlDTO);
    }
}
