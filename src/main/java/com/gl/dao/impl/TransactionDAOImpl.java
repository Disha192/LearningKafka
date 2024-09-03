package com.gl.dao.impl;

import com.gl.dao.TransactionDAO;
import com.gl.entity.Transaction;
import com.gl.util.PayFastUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionDAOImpl implements TransactionDAO {

    @Override
    public void addTransaction(Transaction transaction) throws SQLException {
        String query = "INSERT INTO transactions (transaction_id, account_id, transaction_type, amount, transaction_date, recipient, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = PayFastUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, transaction.getTransaction_id());
            ps.setInt(2, transaction.getAccountId());
            ps.setString(3, transaction.getTransaction_type());
            ps.setInt(4, transaction.getTransaction_amount());
            ps.setTimestamp(5, new java.sql.Timestamp(transaction.getTransaction_date().getTime()));
            ps.setString(6, transaction.getTransaction_recipient());
            ps.setString(7, transaction.getTransaction_status());

            ps.executeUpdate();
        }
    }
}