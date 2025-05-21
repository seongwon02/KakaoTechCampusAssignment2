package com.example.scheduler.lv4.entitiy;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Event extends BaseEntity {
    private Long id;
    private Long user_id;
    private String password;
    private String title;
    private String contents;

    public Event(Long user_id, String password, String title, String contents) {
        this.user_id = user_id;
        this.password = password;
        this.title = title;
        this.contents = contents;
    }

}
