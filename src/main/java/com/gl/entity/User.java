package com.gl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private int user_id;
    private String name;
    private String email;
    private long contact_number;
    private String address;
    private int pin;
    public User() {
        super();
    }
    public User(String name, String email, long contact_number, String address, int pin) {
        super();
        this.name = name;
        this.email = email;
        this.contact_number = contact_number;
        this.address = address;
        this.pin = pin;
    }

}
