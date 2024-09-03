package com.gl.service;

import com.gl.entity.Account;
import com.gl.exceptions.IncorrectPIN;
import com.gl.exceptions.UserNotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface AccountService {
    String addAccount(Account account) throws SQLException, UserNotFoundException;

    List<Integer> getAccountBalance(int userIdBalance, int pinBalance) throws IncorrectPIN;
}
