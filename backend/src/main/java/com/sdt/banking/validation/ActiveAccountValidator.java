package com.sdt.banking.validation;

import java.util.Optional;

public class ActiveAccountValidator extends TransferValidator {

    @Override
    protected Optional<String> check(TransferContext context) {
        if (!context.source().isActive()) {
            return Optional.of("Source account is " + context.source().getStatus());
        }
        if (!context.target().isActive()) {
            return Optional.of("Target account is " + context.target().getStatus());
        }
        return Optional.empty();
    }
}
