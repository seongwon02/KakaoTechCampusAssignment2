package com.example.scheduler.lv5.exception;

public class UsernameAndContentsRequiredException extends RuntimeException{

    public UsernameAndContentsRequiredException() {
        super("Username or Contents are required values");
    }
}
