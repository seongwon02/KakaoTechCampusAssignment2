package com.example.scheduler.lv5.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final int errorCode;
    private final String message;
    private LocalDateTime timestamp;

    public ErrorResponse(int code, String message) {
        this.errorCode = code;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
