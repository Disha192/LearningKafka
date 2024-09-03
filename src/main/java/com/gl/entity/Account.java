package com.gl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Account {
    private int account_id;
    private String account_type;
    private int account_balance;
    private int user_id;

    public Account(String account_type, int account_balance, int user_id) {
        this.account_type = account_type;
        this.account_balance = account_balance;
        this.user_id = user_id;
    }
    @Override
    public String toString() {
        return "Account{" +
                "account_id=" + account_id +
                ", account_type='" + account_type + '\'' +
                ", account_balance=" + account_balance +
                ", user_id=" + user_id +
                '}';
    }
}
