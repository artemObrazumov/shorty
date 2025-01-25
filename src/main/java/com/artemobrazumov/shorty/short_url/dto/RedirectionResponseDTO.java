package com.artemobrazumov.shorty.short_url.dto;

import com.artemobrazumov.shorty.short_url.entity.RedirectionStatus;
import com.artemobrazumov.shorty.short_url.entity.ShortUrl;

import java.time.LocalDateTime;

public record RedirectionResponseDTO(Long id, LocalDateTime redirectionTime, RedirectionStatus status,
                                     String country, String ip, String referer) {}
