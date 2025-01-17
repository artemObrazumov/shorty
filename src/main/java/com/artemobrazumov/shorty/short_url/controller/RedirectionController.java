package com.artemobrazumov.shorty.short_url.controller;

import com.artemobrazumov.shorty.short_url.entity.ShortUrl;
import com.artemobrazumov.shorty.short_url.exceptions.ShortUrlNotFoundException;
import com.artemobrazumov.shorty.short_url.repository.ShortUrlRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class RedirectionController {

    private final ShortUrlRepository shortUrlRepository;

    public RedirectionController(ShortUrlRepository shortUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
    }

    @GetMapping("/{shortUrl}")
    public RedirectView redirectToRealUrl(@PathVariable("shortUrl") String shortUrlString) {
        ShortUrl shortUrl = shortUrlRepository.findByShortUrl(shortUrlString).orElseThrow(() ->
                new ShortUrlNotFoundException(shortUrlString));
        return new RedirectView(shortUrl.getRealUrl());
    }
}
