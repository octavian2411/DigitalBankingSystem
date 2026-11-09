package com.sdt.banking.model;

import com.sdt.banking.fee.FeeStrategy;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Account extends BaseEntity {

    private final String iban;
    private final Long ownerId;
    @Getter(AccessLevel.NONE)
    private final FeeStrategy feeStrategy;
    private BigDecimal balance = BigDecimal.ZERO;
    @Setter
    private AccountStatus status = AccountStatus.ACTIVE;

    public abstract AccountType getType();

    public BigDecimal calculateTransferFee(BigDecimal amount) {
        return feeStrategy.calculateFee(amount);
    }

    public void deposit(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        if (balance.compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient funds");
        }
        balance = balance.subtract(amount);
    }

    public boolean isActive() {
        return status == AccountStatus.ACTIVE;
    }
}
