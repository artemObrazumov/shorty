package com.artemobrazumov.shorty.short_url.controller;

import com.artemobrazumov.shorty.short_url.repository.ShortUrlRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PasswordController {

    private final ShortUrlRepository shortUrlRepository;

    public PasswordController(ShortUrlRepository shortUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
    }

    @GetMapping("/{shortUrl}/password")
    public String shortUrlPassword(@PathVariable("shortUrl") String shortUrlString,
                                   @RequestParam(name = "r_id") Long redirectionId, Model model) {
        model.addAttribute("shortUrl", shortUrlString);
        model.addAttribute("r_id", redirectionId);
        return "password";
    }
}
