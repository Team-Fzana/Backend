package com.example.fzana.domain.member.exception;

public class InvalidMemberException extends RuntimeException {
    public InvalidMemberException(final String message) {
        super(message);
    }
}
