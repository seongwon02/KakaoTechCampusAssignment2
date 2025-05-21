package com.example.scheduler.lv5.repository;

import com.example.scheduler.lv5.entitiy.User;

import java.util.Optional;

public interface UserRepository {

    User saveUser(User user);

    Optional<User> findUserByEmail(String email);

    User findUserByIdOrElseThrow(Long id);

    int updateName(Long id, String name);
}
