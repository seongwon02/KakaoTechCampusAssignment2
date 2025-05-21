package com.example.scheduler.lv3.controller;

import com.example.scheduler.lv3.dto.UserRequestDto;
import com.example.scheduler.lv3.dto.UserResponseDto;
import com.example.scheduler.lv3.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

//    @PostMapping
//    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto dto)
//    {
//        return new ResponseEntity<>(userService.saveUser(dto.getName(), dto.getEmail()), HttpStatus.CREATED);
//    }
}
