package com.sdt.banking.transaction;

public interface TransactionState {

    void complete(Transaction transaction);

    void reject(Transaction transaction, String reason);

    TransactionStatus getStatus();
}
