package com.example.scheduler.lv4.entitiy;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class BaseEntity {

    private LocalDateTime created_at;
    private LocalDateTime modified_at;
}
