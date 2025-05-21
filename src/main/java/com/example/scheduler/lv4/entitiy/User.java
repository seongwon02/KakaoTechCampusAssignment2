package com.example.scheduler.lv4.entitiy;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User extends BaseEntity {
    private Long id;
    private String name;
    private String email;

    public User(String name, String email)
    {
        this.name = name;
        this.email = email;
    }
}
