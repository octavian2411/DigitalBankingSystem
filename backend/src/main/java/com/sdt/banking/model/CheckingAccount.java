package com.sdt.banking.model;

import com.sdt.banking.fee.FeeStrategy;

public class CheckingAccount extends Account {

    public CheckingAccount(String iban, Long ownerId, FeeStrategy feeStrategy) {
        super(iban, ownerId, feeStrategy);
    }

    @Override
    public AccountType getType() {
        return AccountType.CHECKING;
    }
}
