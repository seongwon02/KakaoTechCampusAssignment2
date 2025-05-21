package com.example.scheduler.lv3.service;

import com.example.scheduler.lv3.dto.EventRequestDto;
import com.example.scheduler.lv3.dto.EventResponseDto;
import com.example.scheduler.lv3.entitiy.Event;
import com.example.scheduler.lv3.entitiy.User;
import com.example.scheduler.lv3.repository.EventRepository;
import com.example.scheduler.lv3.repository.UserRepository;
import com.example.scheduler.lv4.dto.EventWithUsernameResponseDto;
import com.example.scheduler.lv4.entitiy.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Transactional
    @Override
    public EventResponseDto saveEvent(EventRequestDto dto) {

        // DB에 해당 이메일을 가진 user 검사(email은 unique함을 이용)
        Optional<User> findUser = userRepository.findUserByEmail(dto.getEmail());
        User user;

        // 이메일이 DB에 존재하지 않는다면 user 새로 생성
        if (findUser.isEmpty()) {
            user = new User(dto.getUsername(), dto.getEmail());
            user = userRepository.saveUser(user);
        }
        else
            user = findUser.get();

        Event event = new Event(
                user.getId(),
                dto.getPassword(),
                dto.getTitle(),
                dto.getContents());

        Event savedEvent = eventRepository.saveEvent(event);
        // log.info("event id: {}", savedEvent.getId());
        return new EventResponseDto(savedEvent);
    }

    @Override
    public List<EventResponseDto> findAllFilteredEvent(Long user_id, LocalDate modified_at) {

        List<EventResponseDto> findEvents = eventRepository.findAllFilteredEvent(user_id, modified_at);

        return findEvents;
    }

    @Override
    public EventResponseDto findEventById(Long id) {

        Event event = eventRepository.findEventByIdOrElseThrow(id);
        User user = userRepository.findUserByIdOrElseThrow(event.getUser_id());

        return new EventResponseDto(event);
    }

    @Transactional
    @Override
    public EventResponseDto updateUsernameOrContents(Long id, String password, String username, String contents) {

        if (password == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password are required values");

        if (username == null && contents == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username or Contents are required values.");

        Event findEvent;

        // id와 일치하는 일정이 있는지 확인 후, 있다면 user_id 획득, 없다면 에러 반환
        if (username != null) {
            findEvent = eventRepository.findEventByIdOrElseThrow(id);
            Long user_id = findEvent.getUser_id();
            userRepository.updateName(user_id, username);
        }

        if (contents != null)
            eventRepository.updateContents(id, contents);

        // id와 일치하는 일정을 꺼내 비밀변호 비교
        findEvent = eventRepository.findEventByIdOrElseThrow(id);
        if (!findEvent.getPassword().equals(password))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password doesn't match.");

        return new EventResponseDto(findEvent);
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
