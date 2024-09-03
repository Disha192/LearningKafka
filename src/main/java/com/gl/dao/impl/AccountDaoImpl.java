package com.gl.dao.impl;

import com.gl.dao.AccountDAO;
import com.gl.entity.Account;
import com.gl.util.PayFastUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl implements AccountDAO {
    @Override
    public void addAccount(Account account) throws SQLException {
        String query = "INSERT INTO accounts (account_id, account_type, balance, user_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = PayFastUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, account.getAccount_id());
            ps.setString(2, account.getAccount_type());
            ps.setInt(3, account.getAccount_balance());
            ps.setInt(4, account.getUser_id());

            ps.executeUpdate();
        }
    }

    @Override
    public List<Integer> getAccountBalance(int userIdBalance) {
        String query = "SELECT balance FROM accounts WHERE user_id = ?";
        List<Integer> balance = new ArrayList<>();
        try (Connection conn = PayFastUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userIdBalance);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    balance.add(rs.getInt("balance"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

    @Override
    public Integer getAccountId(int userPayment) {
        String sql="Select account_id from accounts where user_id=?";
        try (Connection conn = PayFastUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userPayment);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("account_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getRecipientAcountId(String recipient) {
        String sql="Select account_id from accounts where recipient=?";
        try (Connection conn = PayFastUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, recipient);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("account_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
