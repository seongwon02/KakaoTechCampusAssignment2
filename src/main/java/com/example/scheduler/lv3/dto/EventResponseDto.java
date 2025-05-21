package com.example.scheduler.lv3.dto;

import com.example.scheduler.lv3.entitiy.Event;
import com.example.scheduler.lv3.entitiy.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventResponseDto {

    private Long id;
    private Long user_id;
    private String title;
    private String contents;

    public EventResponseDto(Event event) {
        this.id = event.getId();
        this.user_id = event.getUser_id();
        this.title = event.getTitle();
        this.contents = event.getContents();
    }
}
