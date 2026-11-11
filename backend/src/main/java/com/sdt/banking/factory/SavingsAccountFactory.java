package com.sdt.banking.factory;

import com.sdt.banking.fee.PercentageFeeStrategy;
import com.sdt.banking.model.Account;
import com.sdt.banking.model.AccountType;
import com.sdt.banking.model.SavingsAccount;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SavingsAccountFactory extends AccountFactory {

    private static final BigDecimal TRANSFER_FEE_RATE = new BigDecimal("0.01");
    private static final BigDecimal INTEREST_RATE = new BigDecimal("0.02");

    @Override
    public Account createAccount(String iban, Long ownerId) {
        return new SavingsAccount(iban, ownerId, new PercentageFeeStrategy(TRANSFER_FEE_RATE), INTEREST_RATE);
    }

    @Override
    public AccountType supportedType() {
        return AccountType.SAVINGS;
    }
}
