package com.artemobrazumov.shorty.short_url.dto;

import com.artemobrazumov.shorty.short_url.entity.ShortUrl;

import java.time.LocalDateTime;

public record RedirectionDTO(LocalDateTime redirectionTime, ShortUrl shortUrl, String country,
                             String ip, String referer) {}
