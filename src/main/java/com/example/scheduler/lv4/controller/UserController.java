package com.example.scheduler.lv4.controller;

import com.example.scheduler.lv4.service.UserService;
import lombok.RequiredArgsConstructor;
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
