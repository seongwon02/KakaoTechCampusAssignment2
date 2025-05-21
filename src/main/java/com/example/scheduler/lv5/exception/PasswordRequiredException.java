package com.example.scheduler.lv5.exception;

public class PasswordRequiredException extends RuntimeException {

    public PasswordRequiredException() {
        super("Password is required values");
    }
}
