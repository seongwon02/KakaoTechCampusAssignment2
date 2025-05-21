package com.example.scheduler.lv6.exception;

public class EventNotFoundException extends RuntimeException{

    public EventNotFoundException() {
        super("Event doesn't exist.");
    }
}
