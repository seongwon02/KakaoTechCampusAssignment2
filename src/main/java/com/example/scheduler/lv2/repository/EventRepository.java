package com.example.scheduler.lv2.repository;

import com.example.scheduler.lv2.dto.EventResponseDto;
import com.example.scheduler.lv2.entitiy.Event;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository {

    EventResponseDto saveEvent(Event event);

    List<EventResponseDto> findAllFilteredEvent(String username, LocalDate modified_at);

    Event findEventByIdOrElseThrow(Long id);

    int updateUsernameOrContents(Long id, String username, String contents);

    void deleteEvent(Long id);
}
