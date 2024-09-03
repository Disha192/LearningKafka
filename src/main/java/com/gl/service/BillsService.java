package com.gl.service;

import com.gl.entity.Bills;

import java.sql.SQLException;

public interface BillsService {
    String addBill(Bills bill) throws SQLException;

}
