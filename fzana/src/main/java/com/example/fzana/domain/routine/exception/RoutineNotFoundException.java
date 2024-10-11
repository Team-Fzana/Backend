package com.example.fzana.domain.routine.exception;

public class RoutineNotFoundException extends RuntimeException {
    public RoutineNotFoundException(String message){
        super(message);
    }
}
