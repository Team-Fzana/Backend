package com.example.fzana.exception;

public class InvalidUserException extends RuntimeException {
    public InvalidUserException(final String message) {
        super(message);
    }
}
