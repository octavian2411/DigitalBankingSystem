package com.sdt.banking.transaction;

public class CompletedState implements TransactionState {

    @Override
    public void complete(Transaction transaction) {
        throw new IllegalStateException("Transaction is already completed");
    }

    @Override
    public void reject(Transaction transaction, String reason) {
        throw new IllegalStateException("A completed transaction cannot be rejected");
    }

    @Override
    public TransactionStatus getStatus() {
        return TransactionStatus.COMPLETED;
    }
}
