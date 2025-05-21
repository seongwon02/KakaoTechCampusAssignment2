package com.example.scheduler.lv3.repository;

import com.example.scheduler.lv3.dto.UserResponseDto;
import com.example.scheduler.lv3.entitiy.User;

import java.util.Optional;

public interface UserRepository {

    User saveUser(User user);

    Optional<User> findUserByEmail(String email);

    User findUserByIdOrElseThrow(Long id);

    int updateName(Long id, String name);
}
