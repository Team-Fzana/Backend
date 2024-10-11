package com.example.fzana.domain.schedule.exception;

public class ScheduleNotFoundException extends RuntimeException {
    public ScheduleNotFoundException(String message){
        super(message);
    }
}
