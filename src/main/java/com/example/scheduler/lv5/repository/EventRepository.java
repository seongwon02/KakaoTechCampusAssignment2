package com.example.scheduler.lv5.repository;

import com.example.scheduler.lv5.dto.EventWithUsernameResponseDto;
import com.example.scheduler.lv5.entitiy.Event;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository{

    Event saveEvent(Event event);

    List<EventWithUsernameResponseDto> findAllFilteredEvent(Long user_id, LocalDate modified_at, Long offset, Integer size);

    Event findEventByIdOrElseThrow(Long id);

    int updateContents(Long id, String contents);

    void deleteEvent(Long id);

    long countEvents();
}
