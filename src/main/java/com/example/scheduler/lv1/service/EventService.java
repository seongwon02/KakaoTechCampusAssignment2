package com.example.scheduler.lv1.service;

import com.example.scheduler.lv1.dto.EventFilterDto;
import com.example.scheduler.lv1.dto.EventRequestDto;
import com.example.scheduler.lv1.dto.EventResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface EventService {
    EventResponseDto saveEvent(EventRequestDto dto);

    List<EventResponseDto> findAllFilteredEvent(String username, LocalDate modified_at);

    EventResponseDto findEventById(Long id);

}
