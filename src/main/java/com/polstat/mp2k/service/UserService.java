package com.polstat.mp2k.service;

import com.polstat.mp2k.dto.UserDto;


public interface UserService {

    public UserDto createUser(UserDto user);

    public int check(UserDto user, String newPassword);
//    public UserDto getUserByEmail(String email);
}
