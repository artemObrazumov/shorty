package com.artemobrazumov.shorty.registration.exceptions;

public class DuplicateUserException extends RegistrationException {
    public DuplicateUserException(String username) {
        super(String.format("User %s already exists", username));
    }
}
