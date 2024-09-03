package com.gl.dao;

import com.gl.entity.Bills;

import java.sql.SQLException;

public interface BillsDAO {
    void addBills(Bills bills) throws SQLException;

}
