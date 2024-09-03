package com.gl.service;

import com.gl.entity.Biller;

import java.sql.SQLException;

public interface BillerService {
    String addBiller(Biller biller) throws SQLException;

}
