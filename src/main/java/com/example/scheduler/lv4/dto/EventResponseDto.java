package com.example.scheduler.lv4.dto;

import com.example.scheduler.lv4.entitiy.Event;
import com.example.scheduler.lv4.entitiy.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventResponseDto {

    private Long id;
    private Long user_id;
    private String username;
    private String email;
    private String title;
    private String contents;

    public EventResponseDto(Event event, User user) {
        this.id = event.getId();
        this.user_id = user.getId();
        this.username = user.getName();
        this.email = user.getEmail();
        this.title = event.getTitle();
        this.contents = event.getContents();
    }
}
