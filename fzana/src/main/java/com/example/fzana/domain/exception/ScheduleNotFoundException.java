package com.example.fzana.domain.exception;

public class ScheduleNotFoundException extends RuntimeException {
    public ScheduleNotFoundException(String message){
        super(message);
    }
}
