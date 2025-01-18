package com.artemobrazumov.shorty.short_url.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RedirectionNotFoundException extends RuntimeException {
    public RedirectionNotFoundException(Long id) {
        super(String.format("Redirection with id %s not found", id));
    }
}
