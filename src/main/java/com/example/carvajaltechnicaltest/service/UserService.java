package com.example.carvajaltechnicaltest.service;

import com.example.carvajaltechnicaltest.dto.UserDto;
import org.springframework.stereotype.Service;


@Service
public interface UserService {
    UserDto register(UserDto userDto);

    boolean emailExist(String email);


    String login(UserDto userDto);

    String delete(Long id);

    UserDto update(UserDto userDto, Long id);

    UserDto getUser(Long id);
}
