package com.example.scheduler.lv6.dto;

import com.example.scheduler.lv6.entitiy.Page;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageResponseDto {

    private List<EventWithUsernameResponseDto> eventList;
    private Page page;
}
