package com.artemobrazumov.shorty.short_url.advice;

import com.artemobrazumov.shorty.short_url.exceptions.NotShortUrlAuthorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class DomainExceptionHandler {

    @ExceptionHandler(NotShortUrlAuthorException.class)
    public ResponseEntity<Object> handleNotShortUrlAuthorException(NotShortUrlAuthorException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("error", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }
}
