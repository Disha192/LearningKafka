package com.gl.service.impl;

import com.gl.dao.UserDAO;
import com.gl.dto.UserProfileDTO;
import com.gl.entity.User;
import com.gl.exceptions.IncorrectPIN;
import com.gl.exceptions.UserNotFoundException;
import com.gl.service.UserService;
import com.gl.util.PayFastUtil;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public String addUser(User user) throws SQLException {
        int userId = PayFastUtil.generateUniqueId("user_id", "users", 1); // Ensure table name is correct
        User newUser = new User(
                userId,
                user.getName(),
                user.getEmail(),
                user.getContact_number(),
                user.getAddress(),
                user.getPin()
        );
        userDAO.addUsers(newUser);
        return "User registered successfully with ID " + userId;
    }

    @Override
    public UserProfileDTO getUserProfile(int userIdprofile, int pin) throws IncorrectPIN, UserNotFoundException {
        int pinInDatabase = userDAO.getPin(userIdprofile);
        if (pinInDatabase == pin) {
            return userDAO.getUserProfile(userIdprofile);
        }
        return null;
    }
}