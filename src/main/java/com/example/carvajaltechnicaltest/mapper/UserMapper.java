package com.example.carvajaltechnicaltest.mapper;

import com.example.carvajaltechnicaltest.dto.UserDto;
import com.example.carvajaltechnicaltest.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {

   UserDto userToUserDto(User user);
   User userDtoToUser(UserDto userDto);
}
