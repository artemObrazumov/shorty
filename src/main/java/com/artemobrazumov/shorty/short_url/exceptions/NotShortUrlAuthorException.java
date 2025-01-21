package com.artemobrazumov.shorty.short_url.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotShortUrlAuthorException extends RuntimeException {
    public NotShortUrlAuthorException() {
        super("User is not an author of a short url");
    }
}
