package com.gl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Transaction {
    private int transaction_id;
    private int accountId;
    private String transaction_type;
    private int transaction_amount;
    private Date transaction_date;
    private String transaction_recipient;
    private String transaction_status;

    public Transaction(int accountId, String transaction_type, int transaction_amount, Date transaction_date, String transaction_recipient, String transaction_status) {
        this.accountId = accountId;
        this.transaction_type = transaction_type;
        this.transaction_amount = transaction_amount;
        this.transaction_date = transaction_date;
        this.transaction_recipient = transaction_recipient;
        this.transaction_status = transaction_status;
    }
}