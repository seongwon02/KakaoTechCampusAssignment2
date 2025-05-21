package com.example.scheduler.lv6.entitiy;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class BaseEntity {

    private LocalDateTime created_at;
    private LocalDateTime modified_at;
}
