package com.example.scheduler.lv5.service;

import com.example.scheduler.lv5.dto.EventRequestDto;
import com.example.scheduler.lv5.dto.EventResponseDto;
import com.example.scheduler.lv5.dto.EventWithUsernameResponseDto;
import com.example.scheduler.lv5.dto.PageResponseDto;
import com.example.scheduler.lv5.entitiy.Event;
import com.example.scheduler.lv5.entitiy.Page;
import com.example.scheduler.lv5.entitiy.User;
import com.example.scheduler.lv5.exception.PasswordMismatchException;
import com.example.scheduler.lv5.exception.PasswordRequiredException;
import com.example.scheduler.lv5.exception.UsernameAndContentsRequiredException;
import com.example.scheduler.lv5.repository.EventRepository;
import com.example.scheduler.lv5.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
        log.info("event id: {}", savedEvent.getId());
        return new EventResponseDto(savedEvent);
    }

    @Override
    public PageResponseDto findAllFilteredEvent(
            Long user_id,
            LocalDate modified_at,
            Integer page,
            Integer size) {

        Long offset = (page.longValue() - 1) * size;
        long totalElements = eventRepository.countEvents();
        int totalPages = (int) (Math.ceil((double) totalElements / size));

        List<EventWithUsernameResponseDto> findEvents = eventRepository.findAllFilteredEvent(user_id,modified_at, offset, size);
        Page pageInfo = new Page(page, size, totalElements, totalPages);

        return new PageResponseDto(findEvents, pageInfo);
    }

    @Override
    public EventWithUsernameResponseDto findEventById(Long id) {

        Event findEvent = eventRepository.findEventByIdOrElseThrow(id);
        User findUser = userRepository.findUserByIdOrElseThrow(findEvent.getUser_id());

        return new EventWithUsernameResponseDto(findEvent, findUser.getName());
    }

    @Transactional
    @Override
    public EventWithUsernameResponseDto updateUsernameOrContents(Long id, String password, String username, String contents) {

        if (password == null)
            throw new PasswordRequiredException();
            // throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password are required values");

        if (username == null && contents == null)
            throw new UsernameAndContentsRequiredException();
            // throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username or Contents are required values.");

        Event findEvent;
        User findUser;

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
            throw new PasswordMismatchException();
            // throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password doesn't match.");

        findUser = userRepository.findUserByIdOrElseThrow(findEvent.getUser_id());
        return new EventWithUsernameResponseDto(findEvent, findUser.getName());
    }

    @Override
    public void deleteEvent(Long id, String password) {
        if (password == null)
            throw new PasswordRequiredException();
            // throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password are required values");

        Event event = eventRepository.findEventByIdOrElseThrow(id);

        if (!event.getPassword().equals(password))
            throw new PasswordMismatchException();
            //throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password doesn't match.");

        eventRepository.deleteEvent(id);
    }
}
