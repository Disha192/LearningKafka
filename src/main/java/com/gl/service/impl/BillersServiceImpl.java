package com.gl.service.impl;

import com.gl.dao.BillerDAO;
import com.gl.entity.Biller;
import com.gl.service.BillerService;
import com.gl.util.PayFastUtil;

import java.sql.SQLException;

public class BillersServiceImpl implements BillerService {
    private BillerDAO billerDAO;

    public BillersServiceImpl(BillerDAO billerDAO) {
        this.billerDAO = billerDAO;
    }

    @Override
    public String addBiller(Biller biller) throws SQLException {
        int billerId = PayFastUtil.generateUniqueId("biller_id", "billers", 1);

        Biller newBiller = new Biller(
                billerId,
                biller.getBiller_name(),
                biller.getBiller_type(),
                biller.getBiller_account()
        );

        billerDAO.addBiller(newBiller);

        return "Biller recorded successfully with ID " + billerId;
    }
}