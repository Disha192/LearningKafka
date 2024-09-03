package com.gl.dao;

import com.gl.entity.Account;

import java.sql.SQLException;
import java.util.List;

public interface AccountDAO {
    void addAccount (Account account) throws SQLException;

    List<Integer> getAccountBalance(int userIdBalance);

    Integer getAccountId(int userPayment);

    String getRecipientAcountId(String recipient);
}
