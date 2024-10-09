package com.example.fzana.domain.exception;

public class RoutineNotFoundException extends RuntimeException {
    public RoutineNotFoundException(String message){
        super(message);
    }
}
