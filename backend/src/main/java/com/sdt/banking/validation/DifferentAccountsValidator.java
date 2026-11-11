package com.sdt.banking.validation;

import java.util.Optional;

public class DifferentAccountsValidator extends TransferValidator {

    @Override
    protected Optional<String> check(TransferContext context) {
        if (context.source().getId().equals(context.target().getId())) {
            return Optional.of("Source and target accounts must be different");
        }
        return Optional.empty();
    }
}
