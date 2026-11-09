package com.sdt.banking.transaction;

public class PendingState implements TransactionState {

    @Override
    public void complete(Transaction transaction) {
        transaction.changeState(new CompletedState());
    }

    @Override
    public void reject(Transaction transaction, String reason) {
        transaction.setReason(reason);
        transaction.changeState(new RejectedState());
    }

    @Override
    public TransactionStatus getStatus() {
        return TransactionStatus.PENDING;
    }
}
