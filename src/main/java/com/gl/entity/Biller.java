package com.gl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Biller {
    private int biller_id;
    private String biller_name;
    private String biller_type;
    private String biller_account;
    public Biller() {
        super();
    }
    public Biller(String biller_name, String biller_type, String biller_account) {
        super();
        this.biller_name = biller_name;
        this.biller_type = biller_type;
        this.biller_account = biller_account;
    }
}
