package com.example.scheduler.lv1.repository;

import com.example.scheduler.lv1.dto.EventResponseDto;
import com.example.scheduler.lv1.entitiy.Event;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository {

    EventResponseDto saveEvent(Event event);

    List<EventResponseDto> findAllFilteredEvent(String username, LocalDate modified_at);

    Event findEventByIdOrElseThrow(Long id);

}
