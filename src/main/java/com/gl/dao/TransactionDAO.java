package com.gl.dao;

import com.gl.entity.Transaction;

import java.sql.SQLException;

public interface TransactionDAO {
    void addTransaction(Transaction transaction) throws SQLException;
}
