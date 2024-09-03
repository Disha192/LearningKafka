package com.gl.service;

import com.gl.entity.Transaction;
import com.gl.exceptions.IncorrectPIN;

import java.sql.SQLException;

public interface TransactionService {
    String addTransaction(Transaction transaction) throws SQLException;

    String maketransaction(int userPayment,String transactionType, int pinPayment, int amountPayment, String recipient) throws IncorrectPIN, SQLException;
}