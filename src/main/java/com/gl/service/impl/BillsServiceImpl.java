package com.gl.service.impl;

import com.gl.dao.BillsDAO;
import com.gl.entity.Bills;
import com.gl.service.BillsService;
import com.gl.util.PayFastUtil;

import java.sql.SQLException;

public class BillsServiceImpl implements BillsService {
    private BillsDAO billDAO;

    public BillsServiceImpl(BillsDAO billDAO) {
        this.billDAO = billDAO;
    }

    @Override
    public String addBill(Bills bill) throws SQLException {
        // Generate a unique ID for the bill
        int billId = PayFastUtil.generateUniqueId("bill_id", "bills", 1);

        // Create a new bill with the generated ID and other details
        Bills newBill = new Bills(
                billId,
                bill.getBiller_id(),
                bill.getAccount_id(),
                bill.getAmount(), // Changed from getBill_amount() to getAmount()
                bill.getPayment_date(),
                bill.getStatus() // Changed from getBill_status() to getStatus()
        );

        // Persist the new bill using the DAO
        billDAO.addBills(newBill);

        // Return a success message
        return "Bill recorded successfully with ID " + billId;
    }
}