package com.sdt.banking.model;

import com.sdt.banking.fee.FeeStrategy;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class SavingsAccount extends Account {

    private final BigDecimal interestRate;

    public SavingsAccount(String iban, Long ownerId, FeeStrategy feeStrategy, BigDecimal interestRate) {
        super(iban, ownerId, feeStrategy);
        this.interestRate = interestRate;
    }

    @Override
    public AccountType getType() {
        return AccountType.SAVINGS;
    }
}
