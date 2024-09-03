package com.gl.service.impl;

import com.gl.dao.AccountDAO;
import com.gl.dao.TransactionDAO;
import com.gl.dao.UserDAO;
import com.gl.entity.Transaction;
import com.gl.exceptions.IncorrectPIN;
import com.gl.service.TransactionService;
import com.gl.util.PayFastUtil;

import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class TransactionServiceImpl implements TransactionService {
    private final UserDAO userDAO;
    private TransactionDAO transactionDAO;
    private AccountDAO accountDAO;


    public TransactionServiceImpl(TransactionDAO transactionDAO, UserDAO usersDAO, AccountDAO accountDAO) {
        this.transactionDAO = transactionDAO;
        this.userDAO = usersDAO;
        this.accountDAO = accountDAO;
    }

    @Override
    public String addTransaction(Transaction transaction) throws SQLException {
        int transactionId = PayFastUtil.generateUniqueId("transaction_id", "transactions", 1);
        Transaction newTransaction = new Transaction(
                transactionId,
                transaction.getAccountId(),
                transaction.getTransaction_type(),
                transaction.getTransaction_amount(),
                transaction.getTransaction_date(),
                transaction.getTransaction_recipient(),
                transaction.getTransaction_status()
        );
        transactionDAO.addTransaction(newTransaction);
        AuditLogging.logEvent(transactionId,transaction.getAccountId(),transaction.getTransaction_recipient(),transaction.getTransaction_date(),transaction.getTransaction_type());
        return "Transaction recorded successfully with id " + transactionId;
    }

    @Override
    public String maketransaction(int userPayment,String transactionType, int pinPayment, int amountPayment, String recipient) throws IncorrectPIN, SQLException {
        int pinInDatabase = userDAO.getPin(userPayment);
        if (pinInDatabase != pinPayment) {
            throw new IncorrectPIN("Incorrect PIN");
        }
        int accountId = accountDAO.getAccountId(userPayment);
        StatusandTimeUpdate statusChanger = new StatusandTimeUpdate();
        statusChanger.startStatusChange();
        try {
            Thread.sleep(3000); // Simulate processing time
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        statusChanger.stopStatusChange();
        String status = statusChanger.getStatus();
        Date today = new StatusandTimeUpdate().getDate();
        Transaction transaction = new Transaction(accountId, transactionType, amountPayment, today, recipient, "Completed");
        return addTransaction(transaction);
    }
}