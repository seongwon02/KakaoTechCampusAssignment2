package com.example.scheduler.lv3.service;

import com.example.scheduler.lv3.dto.EventRequestDto;
import com.example.scheduler.lv3.dto.EventResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface EventService {
    EventResponseDto saveEvent(EventRequestDto dto);

    List<EventResponseDto> findAllFilteredEvent(String username, LocalDate modified_at);

    EventResponseDto findEventById(Long id);

    EventResponseDto updateUsernameOrContents(Long id, String password, String username, String contents);

    void deleteEvent(Long id, String password);
}
