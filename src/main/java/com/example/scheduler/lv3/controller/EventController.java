package com.example.scheduler.lv3.controller;

import com.example.scheduler.lv3.dto.EventFilterRequestDto;
import com.example.scheduler.lv3.dto.EventRequestDto;
import com.example.scheduler.lv3.dto.EventResponseDto;
import com.example.scheduler.lv3.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/schedule")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<EventResponseDto> createEvent(@RequestBody EventRequestDto dto) {

        return new ResponseEntity<>(eventService.saveEvent(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EventResponseDto>> FindAllFilteredEvent(@RequestBody EventFilterRequestDto dto) {

        List<EventResponseDto> eventResponseDtoList = eventService.findAllFilteredEvent(
                dto.getUser_id(),
                dto.getModified_at());

        return new ResponseEntity<>(eventResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<EventResponseDto> FindEventById (@PathVariable Long id) {

        return new ResponseEntity<>(eventService.findEventById(id), HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<EventResponseDto> updateUsernameOrContents(
            @PathVariable Long id,
            @RequestBody EventRequestDto dto
    ){
        return new ResponseEntity<>(eventService.updateUsernameOrContents(id, dto.getPassword(), dto.getUsername(), dto.getContents()), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id, @RequestBody EventRequestDto dto)
    {
        eventService.deleteEvent(id, dto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
