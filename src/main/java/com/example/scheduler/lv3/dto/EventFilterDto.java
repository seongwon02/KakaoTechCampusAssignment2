package com.example.scheduler.lv3.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class EventFilterDto {
    private String username;
    LocalDate modified_at;
}
