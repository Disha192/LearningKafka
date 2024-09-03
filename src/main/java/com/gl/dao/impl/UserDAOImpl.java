package com.gl.dao.impl;

import com.gl.dao.UserDAO;
import com.gl.dto.UserProfileDTO;
import com.gl.entity.Account;
import com.gl.entity.User;
import com.gl.exceptions.IncorrectPIN;
import com.gl.exceptions.UserNotFoundException;
import com.gl.util.PayFastUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public void addUsers(User user) throws SQLException {
        String query = "INSERT INTO users (user_id, name, email, contact_number, address, pin) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = PayFastUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, user.getUser_id());
            ps.setString(2, user.getName());
            ps.setString(3, user.getEmail());
            ps.setLong(4, user.getContact_number());
            ps.setString(5, user.getAddress());
            ps.setInt(6, user.getPin());
            ps.executeUpdate();
        }
    }

    @Override
    public int getPin(int userIdprofile) throws IncorrectPIN {
        String sql = "SELECT pin FROM users WHERE user_id = ?";
        try (Connection conn = PayFastUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userIdprofile);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("pin");
                } else {
                    throw new IncorrectPIN("Incorrect PIN");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
        public UserProfileDTO getUserProfile(int userIdprofile) throws UserNotFoundException {
            String sql = "SELECT * FROM users WHERE user_id = ?";
            String sql2 = "SELECT * FROM accounts WHERE user_id = ?";
            User user = null;
            List<Account> accounts = new ArrayList<>();
            try (Connection conn = PayFastUtil.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userIdprofile);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        user = new User(
                                rs.getInt("user_id"),
                                rs.getString("user_name"),
                                rs.getString("email"),
                                rs.getLong("contact_number"),
                                rs.getString("address"),
                                rs.getInt("pin")
                        );
                    } else {
                        throw new UserNotFoundException("User not found with ID: " + userIdprofile);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
            try (Connection conn = PayFastUtil.getConnection();
                 PreparedStatement stmt1 = conn.prepareStatement(sql2)) {
                stmt1.setInt(1, userIdprofile);
                try (ResultSet rs = stmt1.executeQuery()) {
                    while (rs.next()) {
                        Account account = new Account(
                                rs.getInt("account_id"),
                                rs.getString("account_type"),
                                rs.getInt("balance"),
                                rs.getInt("user_id")
                        );
                        accounts.add(account);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
            UserProfileDTO userProfileDTO = new UserProfileDTO(
                    user.getUser_id(),
                    user.getName(),
                    user.getEmail(),
                    accounts
            );

            return userProfileDTO;
        }
    @Override
    public User getUserById(int userId) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (Connection conn = PayFastUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User(
                            rs.getInt("user_id"),
                            rs.getString("user_name"),
                            rs.getString("email"),
                            rs.getLong("contact_number"),
                            rs.getString("address"),
                            rs.getInt("pin")
                    );
                    System.out.println(user);
                    if (user == null) {
                        System.out.println("Null is incurred");
                        return null;
                    }
                    return user;
                } else {
                    return null;
                }
            }
        }
    }
}
