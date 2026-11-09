package com.sdt.banking.notification;

import com.sdt.banking.transaction.Transaction;

public interface TransactionObserver {

    void onTransactionFinished(Transaction transaction);
}
