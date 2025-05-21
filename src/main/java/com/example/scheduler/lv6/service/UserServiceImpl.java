package com.example.scheduler.lv6.service;

import com.example.scheduler.lv6.dto.UserResponseDto;
import com.example.scheduler.lv6.entitiy.User;
import com.example.scheduler.lv6.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponseDto saveUser(String name, String email) {

        User user = new User(name, email);

        Optional<User> findUser = userRepository.findUserByEmail(email);

        if (findUser.isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This email already exists.");

        User savedUser = userRepository.saveUser(user);
        return new UserResponseDto(savedUser);
    }
}
