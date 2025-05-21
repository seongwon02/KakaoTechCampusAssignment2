package com.example.scheduler.lv3.repository;

import com.example.scheduler.lv3.dto.EventResponseDto;
import com.example.scheduler.lv3.entitiy.Event;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository {

    Event saveEvent(Event event);

    List<EventResponseDto> findAllFilteredEvent(Long user_id, LocalDate modified_at);

    Event findEventByIdOrElseThrow(Long id);

    int updateContents(Long id, String contents);

    void deleteEvent(Long id);
}
