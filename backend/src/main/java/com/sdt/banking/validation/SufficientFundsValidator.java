package com.sdt.banking.validation;

import java.util.Optional;

public class SufficientFundsValidator extends TransferValidator {

    @Override
    protected Optional<String> check(TransferContext context) {
        if (context.source().getBalance().compareTo(context.totalDebit()) < 0) {
            return Optional.of("Insufficient funds (needed " + context.totalDebit()
                    + ", available " + context.source().getBalance() + ")");
        }
        return Optional.empty();
    }
}
