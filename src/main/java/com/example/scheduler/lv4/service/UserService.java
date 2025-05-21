package com.example.scheduler.lv4.service;

import com.example.scheduler.lv4.dto.UserResponseDto;

public interface UserService {

    UserResponseDto saveUser(String name, String email);
}
