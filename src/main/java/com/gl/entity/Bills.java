package com.gl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Bills {
    private int bill_id;
    private int biller_id;
    private int account_id;
    private double amount; // Changed from bill_amount to amount
    private Date payment_date;
    private String status; // Changed from bill_status to status

    public Bills() {
        super();
    }

    public Bills(int biller_id, int account_id, double amount, Date payment_date, String status) {
        super();
        this.biller_id = biller_id;
        this.account_id = account_id;
        this.amount = amount;
        this.payment_date = payment_date;
        this.status = status;
    }
}