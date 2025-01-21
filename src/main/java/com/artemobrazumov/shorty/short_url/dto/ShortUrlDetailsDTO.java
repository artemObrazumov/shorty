package com.artemobrazumov.shorty.short_url.dto;

public record ShortUrlDetailsDTO(Long id, String name, String url, String shortUrl, Boolean isProtected,
                                 Boolean isActive) {}
