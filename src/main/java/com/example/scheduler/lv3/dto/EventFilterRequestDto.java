package com.example.scheduler.lv3.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class EventFilterRequestDto {
    private Long user_id;
    LocalDate modified_at;
}
