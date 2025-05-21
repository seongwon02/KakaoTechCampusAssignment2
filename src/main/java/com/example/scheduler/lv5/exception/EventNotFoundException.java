package com.example.scheduler.lv5.exception;

public class EventNotFoundException extends RuntimeException{

    public EventNotFoundException() {
        super("Event doesn't exist.");
    }
}
