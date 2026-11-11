package com.sdt.banking.factory;

import com.sdt.banking.model.Account;
import com.sdt.banking.model.AccountType;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

public abstract class AccountFactory {

    private static final AtomicLong IBAN_SEQUENCE = new AtomicLong(1);

    public Account openAccount(Long ownerId, BigDecimal initialDeposit) {
        Account account = createAccount(nextIban(), ownerId);
        if (initialDeposit != null && initialDeposit.signum() > 0) {
            account.deposit(initialDeposit);
        }
        return account;
    }

    protected abstract Account createAccount(String iban, Long ownerId);

    public abstract AccountType supportedType();

    private String nextIban() {
        return String.format("RO49SDTB%016d", IBAN_SEQUENCE.getAndIncrement());
    }
}
