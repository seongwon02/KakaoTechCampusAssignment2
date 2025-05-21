package com.example.scheduler.lv3.service;

import com.example.scheduler.lv3.dto.UserRequestDto;
import com.example.scheduler.lv3.dto.UserResponseDto;

public interface UserService {

    UserResponseDto saveUser(String name, String email);
}
