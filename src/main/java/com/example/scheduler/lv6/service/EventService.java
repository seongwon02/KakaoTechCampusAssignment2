package com.example.scheduler.lv6.service;

import com.example.scheduler.lv6.dto.EventRequestDto;
import com.example.scheduler.lv6.dto.EventResponseDto;
import com.example.scheduler.lv6.dto.EventWithUsernameResponseDto;
import com.example.scheduler.lv6.dto.PageResponseDto;

import java.time.LocalDate;

public interface EventService {
    EventResponseDto saveEvent(EventRequestDto dto);

    PageResponseDto findAllFilteredEvent(Long user_id, LocalDate modified_at, Integer page, Integer size);

    EventWithUsernameResponseDto findEventById(Long id);

    EventWithUsernameResponseDto updateUsernameOrContents(Long id, String password, String username, String contents);

    void deleteEvent(Long id, String password);
}
