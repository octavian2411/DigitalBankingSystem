package com.sdt.banking.validation;

import java.util.Optional;

public abstract class TransferValidator {

    private TransferValidator next;

    public TransferValidator setNext(TransferValidator next) {
        this.next = next;
        return next;
    }

    public Optional<String> validate(TransferContext context) {
        Optional<String> error = check(context);
        if (error.isPresent() || next == null) {
            return error;
        }
        return next.validate(context);
    }

    protected abstract Optional<String> check(TransferContext context);
}
