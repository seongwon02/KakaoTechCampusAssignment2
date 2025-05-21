package com.example.scheduler.lv5.service;

import com.example.scheduler.lv5.dto.UserResponseDto;

public interface UserService {

    UserResponseDto saveUser(String name, String email);
}
