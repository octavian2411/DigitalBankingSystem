package com.sdt.banking.validation;

import com.sdt.banking.model.Account;

import java.math.BigDecimal;

public record TransferContext(Account source, Account target, BigDecimal amount, BigDecimal fee) {

    public BigDecimal totalDebit() {
        return amount.add(fee);
    }
}
