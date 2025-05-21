package com.example.scheduler.lv3.dto;

import com.example.scheduler.lv3.entitiy.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventResponseDto {

    private Long id;
    private String username;
    private String title;
    private String contents;

    public EventResponseDto(Event event) {
        this.id = event.getId();
        this.username = event.getUsername();
        this.title = event.getTitle();
        this.contents = event.getContents();
    }
}
