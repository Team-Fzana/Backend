package com.example.fzana.exception;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException(String message){
        super(message);
    }
}
