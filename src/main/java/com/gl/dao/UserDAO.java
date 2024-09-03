package com.gl.dao;

import com.gl.dto.UserProfileDTO;
import com.gl.entity.User;
import com.gl.exceptions.IncorrectPIN;
import com.gl.exceptions.UserNotFoundException;

import java.sql.SQLException;

public interface UserDAO {
    void addUsers(User user) throws SQLException;

    int getPin(int userIdprofile) throws IncorrectPIN;

    UserProfileDTO getUserProfile(int userIdprofile) throws UserNotFoundException;

    User getUserById(int userId) throws UserNotFoundException, SQLException;
}
