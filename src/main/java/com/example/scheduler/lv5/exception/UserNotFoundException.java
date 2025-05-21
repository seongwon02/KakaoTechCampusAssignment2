package com.example.scheduler.lv5.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException() {
        super("User doesn't exist.");
    }
}
