package com.example.scheduler.lv6.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class EventRequestDto {

    private String username;

    @Email
    private String email;

    @NotBlank
    private String password;

    private String title;

    @NotBlank
    @Size(max = 200)
    private String contents;

}
