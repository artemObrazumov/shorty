package com.artemobrazumov.shorty.short_url.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ShortUrlDTO(
        @NotNull(message = "Name of short url is required")
        @Size(min = 3, max = 30, message = "Incorrect name size")
        String name,
        @NotNull(message = "Real url is required")
        @NotBlank(message = "Real url should not be blank")
        @Size(max = 200, message = "Incorrect real url size")
        @Pattern(regexp = "^(http|https)://.*", message = "Invalid url pattern")
        String url,
        @Size(max = 25, message = "Incorrect short url size")
        String shortUrl,
        @Size(min = 3, max = 70, message = "Incorrect password size")
        String password
) {}
