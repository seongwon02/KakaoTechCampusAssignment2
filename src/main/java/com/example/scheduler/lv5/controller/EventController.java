package com.example.scheduler.lv5.controller;

import com.example.scheduler.lv5.dto.*;
import com.example.scheduler.lv5.exception.*;
import com.example.scheduler.lv5.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<PageResponseDto> FindAllFilteredEvent(
            @RequestBody EventFilterRequestDto dto,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size) {

        PageResponseDto pageResponseDto = eventService.findAllFilteredEvent(
                dto.getUser_id(), dto.getModified_at(), page, size);

        return new ResponseEntity<>(pageResponseDto, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<EventWithUsernameResponseDto> FindEventById (@PathVariable Long id) {

        return new ResponseEntity<>(eventService.findEventById(id), HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<EventWithUsernameResponseDto> updateUsernameOrContents(
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

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorResponse> handlePasswordMismatchException(PasswordMismatchException e) {
        ErrorResponse errorResponse = new ErrorResponse(401, e.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PasswordRequiredException.class)
    public ResponseEntity<ErrorResponse> handleArgumentIsNull(PasswordRequiredException e) {
        ErrorResponse errorResponse = new ErrorResponse(400, e.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEventNotFound(EventNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(404, e.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameAndContentsRequiredException.class)
    public ResponseEntity<ErrorResponse> handleUsernameAndContentsIsNull(UsernameAndContentsRequiredException e) {
        ErrorResponse errorResponse = new ErrorResponse(400, e.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(404, e.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
