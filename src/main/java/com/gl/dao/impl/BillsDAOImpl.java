package com.gl.dao.impl;

import com.gl.dao.BillsDAO;
import com.gl.entity.Bills;
import com.gl.util.PayFastUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BillsDAOImpl implements BillsDAO {
    public void addBills(Bills bill) throws SQLException {
        String query = "INSERT INTO bills (bill_id, biller_id, account_id, amount, payment_date, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = PayFastUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, bill.getBill_id());
            ps.setInt(2, bill.getBiller_id());
            ps.setInt(3, bill.getAccount_id());
            ps.setDouble(4, bill.getAmount());
            ps.setDate(5, new java.sql.Date(bill.getPayment_date().getTime()));
            ps.setString(6, bill.getStatus());
            ps.executeUpdate();
        }
    }
}