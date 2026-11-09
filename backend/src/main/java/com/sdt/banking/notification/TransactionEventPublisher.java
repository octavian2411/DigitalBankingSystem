package com.sdt.banking.notification;

import com.sdt.banking.transaction.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class TransactionEventPublisher {

    private final List<TransactionObserver> observers = new CopyOnWriteArrayList<>();

    public TransactionEventPublisher(List<TransactionObserver> initialObservers) {
        observers.addAll(initialObservers);
    }

    public void subscribe(TransactionObserver observer) {
        observers.add(observer);
    }

    public void unsubscribe(TransactionObserver observer) {
        observers.remove(observer);
    }

    public void publish(Transaction transaction) {
        observers.forEach(observer -> observer.onTransactionFinished(transaction));
    }
}
