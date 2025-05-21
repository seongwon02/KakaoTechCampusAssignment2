package com.example.scheduler.lv6.exception;

public class PasswordRequiredException extends RuntimeException {

    public PasswordRequiredException() {
        super("Password is required values");
    }
}
