package com.artemobrazumov.shorty.short_url.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateShortUrlException extends RuntimeException {
    public DuplicateShortUrlException(String shortUrl) {
        super("Short url " + shortUrl + " is already in use");
    }
}

