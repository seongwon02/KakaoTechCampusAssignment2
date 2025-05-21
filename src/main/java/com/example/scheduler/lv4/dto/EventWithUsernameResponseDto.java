package com.example.scheduler.lv4.dto;

import com.example.scheduler.lv4.entitiy.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventWithUsernameResponseDto {
    private Long id;
    private Long user_id;
    private String username;
    private String title;
    private String contents;

    public EventWithUsernameResponseDto(Event event, String username){
        this.id = event.getId();
        this.user_id = event.getUser_id();
        this.username = username;
        this.title = event.getTitle();
        this.contents = event.getContents();
    }

}
