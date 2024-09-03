package com.gl.service.impl;

import com.gl.dao.AccountDAO;
import com.gl.dao.UserDAO;
import com.gl.entity.Account;
import com.gl.entity.User;
import com.gl.exceptions.IncorrectPIN;
import com.gl.exceptions.UserNotFoundException;
import com.gl.service.AccountService;
import com.gl.util.PayFastUtil;

import java.sql.SQLException;
import java.util.List;

public class AccountServiceImpl implements AccountService {
    private final UserDAO userDAO;
    private final AccountDAO accountDAO;

    public AccountServiceImpl(AccountDAO accountDAO, UserDAO userDAO) {
        this.accountDAO = accountDAO;
        this.userDAO = userDAO;
    }

    @Override
    public String addAccount(Account account) throws SQLException, UserNotFoundException {
        int accountId = PayFastUtil.generateUniqueId("account_id", "accounts", 1);
        Account newAccount = new Account(
                accountId,
                account.getAccount_type(),
                account.getAccount_balance(),
                account.getUser_id()
        );

        User user = userDAO.getUserById(account.getUser_id());
        if (user == null) {
            throw new UserNotFoundException("User not found with ID: " + account.getUser_id());
        }

        accountDAO.addAccount(newAccount);
        return "Account recorded successfully with ID " + accountId;
    }

    @Override
    public List<Integer> getAccountBalance(int userIdBalance, int pinBalance) throws IncorrectPIN {
        int pinInDatabase = userDAO.getPin(userIdBalance);
        if (pinInDatabase == pinBalance) {
            return accountDAO.getAccountBalance(userIdBalance);
        }
        return null;
    }
}
