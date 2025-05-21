package com.example.scheduler.lv4.dto;

import com.example.scheduler.lv4.entitiy.Event;
import com.example.scheduler.lv4.entitiy.Page;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageResponseDto {

    private List<EventWithUsernameResponseDto> eventList;
    private Page page;
}
