package com.example.scheduler.lv6.service;

import com.example.scheduler.lv6.dto.UserResponseDto;

public interface UserService {

    UserResponseDto saveUser(String name, String email);
}
