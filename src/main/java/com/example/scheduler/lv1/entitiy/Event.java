package com.example.scheduler.lv1.entitiy;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Event extends BaseEntity{
    private Long id;
    private String username;
    private String password;
    private String title;
    private String contents;

    public Event(String username, String password, String title, String contents) {
        this.username = username;
        this.password = password;
        this.title = title;
        this.contents = contents;
    }
}
