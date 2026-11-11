package com.sdt.banking.validation;

import java.util.Optional;

public class PositiveAmountValidator extends TransferValidator {

    @Override
    protected Optional<String> check(TransferContext context) {
        if (context.amount().signum() <= 0) {
            return Optional.of("Amount must be greater than zero");
        }
        return Optional.empty();
    }
}
