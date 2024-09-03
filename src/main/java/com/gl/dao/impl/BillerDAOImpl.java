package com.gl.dao.impl;

import com.gl.dao.BillerDAO;
import com.gl.entity.Biller;
import com.gl.util.PayFastUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BillerDAOImpl implements BillerDAO {
    @Override
    public void addBiller(Biller biller) throws SQLException {
        String query = "INSERT INTO billers (biller_id, biller_name, biller_type, biller_account) VALUES (?, ?, ?, ?)";

        try (Connection conn = PayFastUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, biller.getBiller_id());
            ps.setString(2, biller.getBiller_name());
            ps.setString(3, biller.getBiller_type());
            ps.setString(4, biller.getBiller_account());

            ps.executeUpdate();
        }
    }
}
