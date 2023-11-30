package com.polstat.mp2k.mapper;

import com.polstat.mp2k.dto.UserDto;
import com.polstat.mp2k.entity.User;


public class UserMapper {

    public static User mapToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .NIM(userDto.getNIM())
                .build();
    }

    public static UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .password(user.getPassword())
                .email(user.getEmail())
                .NIM(user.getNIM())
                .build();
    }
}
