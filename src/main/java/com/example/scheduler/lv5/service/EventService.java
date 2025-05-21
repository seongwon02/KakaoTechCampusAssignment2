package com.example.scheduler.lv5.service;

import com.example.scheduler.lv5.dto.EventRequestDto;
import com.example.scheduler.lv5.dto.EventResponseDto;
import com.example.scheduler.lv5.dto.EventWithUsernameResponseDto;
import com.example.scheduler.lv5.dto.PageResponseDto;

import java.time.LocalDate;

public interface EventService {
    EventResponseDto saveEvent(EventRequestDto dto);

    PageResponseDto findAllFilteredEvent(Long user_id, LocalDate modified_at, Integer page, Integer size);

    EventWithUsernameResponseDto findEventById(Long id);

    EventWithUsernameResponseDto updateUsernameOrContents(Long id, String password, String username, String contents);

    void deleteEvent(Long id, String password);
}
