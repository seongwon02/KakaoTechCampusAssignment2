package com.example.scheduler.lv3.service;

import com.example.scheduler.lv3.dto.UserRequestDto;
import com.example.scheduler.lv3.dto.UserResponseDto;
import com.example.scheduler.lv3.entitiy.User;
import com.example.scheduler.lv3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

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
