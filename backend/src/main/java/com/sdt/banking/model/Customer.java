package com.sdt.banking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Customer extends BaseEntity {

    private String name;
    private String email;

    public void update(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
