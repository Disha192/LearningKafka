package com.gl.service;

import com.gl.dto.UserProfileDTO;
import com.gl.entity.User;
import com.gl.exceptions.IncorrectPIN;
import com.gl.exceptions.UserNotFoundException;

import java.sql.SQLException;

public interface UserService {

    String addUser(User user) throws SQLException;

    UserProfileDTO getUserProfile(int userIdprofile, int pin) throws IncorrectPIN, UserNotFoundException;
}
