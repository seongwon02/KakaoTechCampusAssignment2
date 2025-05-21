package com.example.scheduler.lv6.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException() {
        super("User doesn't exist.");
    }
}
