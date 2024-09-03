package com.gl.dao;

import com.gl.entity.Biller;

import java.sql.SQLException;

public interface BillerDAO {
    void addBiller(Biller biller) throws SQLException;

}
