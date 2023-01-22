package com.web.furniturehub.service;

import java.util.List;

import com.web.furniturehub.dto.UserDto;
import com.web.furniturehub.model.User;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}