package com.artemobrazumov.shorty.short_url.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ShortUrlUpdateDTO(
        @NotNull(message = "Name of short url is required")
        @Size(min = 3, max = 30, message = "Incorrect name size")
        String name
) {}
