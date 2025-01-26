package com.artemobrazumov.shorty.registration.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegistrationDTO(
        @NotNull(message = "Name of short url is required")
        @Size(min = 3, max = 20, message = "Incorrect name size")
        String name,
        @NotNull(message = "Password is required")
        @Size(min = 3, max = 70, message = "Incorrect password size")
        String password
) {}
