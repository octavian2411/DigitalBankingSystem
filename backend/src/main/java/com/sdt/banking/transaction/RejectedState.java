package com.sdt.banking.transaction;

public class RejectedState implements TransactionState {

    @Override
    public void complete(Transaction transaction) {
        throw new IllegalStateException("A rejected transaction cannot be completed");
    }

    @Override
    public void reject(Transaction transaction, String reason) {
        throw new IllegalStateException("Transaction is already rejected");
    }

    @Override
    public TransactionStatus getStatus() {
        return TransactionStatus.REJECTED;
    }
}
