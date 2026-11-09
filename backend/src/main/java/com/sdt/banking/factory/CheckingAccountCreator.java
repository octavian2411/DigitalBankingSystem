package com.sdt.banking.factory;

import com.sdt.banking.fee.NoFeeStrategy;
import com.sdt.banking.model.Account;
import com.sdt.banking.model.AccountType;
import com.sdt.banking.model.CheckingAccount;
import org.springframework.stereotype.Component;

@Component
public class CheckingAccountCreator extends AccountCreator {

    @Override
    public Account createAccount(String iban, Long ownerId) {
        return new CheckingAccount(iban, ownerId, new NoFeeStrategy());
    }

    @Override
    public AccountType supportedType() {
        return AccountType.CHECKING;
    }
}
