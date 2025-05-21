package com.example.scheduler.lv4.service;

import com.example.scheduler.lv4.dto.EventRequestDto;
import com.example.scheduler.lv4.dto.EventResponseDto;
import com.example.scheduler.lv4.dto.EventWithUsernameResponseDto;
import com.example.scheduler.lv4.dto.PageResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface EventService {
    EventResponseDto saveEvent(EventRequestDto dto);

    PageResponseDto findAllFilteredEvent(Long user_id, LocalDate modified_at, Integer page, Integer size);

    EventWithUsernameResponseDto findEventById(Long id);

    EventWithUsernameResponseDto updateUsernameOrContents(Long id, String password, String username, String contents);

    void deleteEvent(Long id, String password);
}
