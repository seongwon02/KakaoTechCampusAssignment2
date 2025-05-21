package com.example.scheduler.lv2.service;

import com.example.scheduler.lv2.dto.EventRequestDto;
import com.example.scheduler.lv2.dto.EventResponseDto;
import com.example.scheduler.lv2.entitiy.Event;
import com.example.scheduler.lv2.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class EventServiceImpl implements EventService {

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

    @Transactional
    @Override
    public EventResponseDto updateUsernameOrContents(Long id, String password, String username, String contents) {

        if (password == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password are required values");

        if (username == null && contents == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username or Contents are required values.");

        int updateRow = eventRepository.updateUsernameOrContents(id, username, contents);

        if (updateRow == 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);

        Event event = eventRepository.findEventByIdOrElseThrow(id);

        if (!event.getPassword().equals(password))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password doesn't match.");

        return new EventResponseDto(event);
    }

    @Override
    public void deleteEvent(Long id, String password) {
        if (password == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password are required values");

        Event event = eventRepository.findEventByIdOrElseThrow(id);

        if (!event.getPassword().equals(password))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password doesn't match.");

        eventRepository.deleteEvent(id);
    }
}
