package com.example.scheduler.lv1.service;

import com.example.scheduler.lv1.dto.EventRequestDto;
import com.example.scheduler.lv1.dto.EventResponseDto;
import com.example.scheduler.lv1.entitiy.Event;
import com.example.scheduler.lv1.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventServiceImpl implements EventService{

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public EventResponseDto saveEvent(EventRequestDto dto) {

        Event event = new Event(
                dto.getUsername(),
                dto.getPassword(),
                dto.getTitle(),
                dto.getContents());

        return eventRepository.saveEvent(event);
    }

    @Override
    public List<EventResponseDto> findAllFilteredEvent(String username, LocalDate modified_at) {

        return eventRepository.findAllFilteredEvent(username, modified_at);
    }

    @Override
    public EventResponseDto findEventById(Long id) {

        Event event = eventRepository.findEventByIdOrElseThrow(id);
        return new EventResponseDto(event);
    }

}
