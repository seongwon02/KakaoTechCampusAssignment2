package com.example.scheduler.lv5.exception;

public class PasswordMismatchException extends RuntimeException {

    public PasswordMismatchException() {
        super("Password doesn't match.");
    }
}
