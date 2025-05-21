package com.example.scheduler.lv1.dto;

import com.example.scheduler.lv1.entitiy.Event;
import lombok.Getter;

@Getter
public class EventRequestDto {

    private String username;
    private String password;
    private String title;
    private String contents;
}
