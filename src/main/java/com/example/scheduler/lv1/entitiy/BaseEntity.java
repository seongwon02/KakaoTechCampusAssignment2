package com.example.scheduler.lv1.entitiy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public abstract class BaseEntity {

    private LocalDateTime created_at;
    private LocalDateTime modified_at;
}
