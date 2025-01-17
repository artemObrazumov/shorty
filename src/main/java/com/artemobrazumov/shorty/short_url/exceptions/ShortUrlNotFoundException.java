package com.artemobrazumov.shorty.short_url.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ShortUrlNotFoundException extends RuntimeException {
    public ShortUrlNotFoundException(String shortUrl) {
        super("Short url " + shortUrl + " not found");
    }
}
