package com.artemobrazumov.shorty.short_url.dto;

import java.time.LocalDateTime;

public record ShortUrlResponseDTO(Long id, String name, String url, String shortUrl, LocalDateTime createdAt,
                                  LocalDateTime editedAt, Integer redirections) {}
