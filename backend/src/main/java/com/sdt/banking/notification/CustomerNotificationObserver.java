package com.sdt.banking.notification;

import com.sdt.banking.model.Account;
import com.sdt.banking.model.Notification;
import com.sdt.banking.repository.AccountRepository;
import com.sdt.banking.repository.NotificationRepository;
import com.sdt.banking.transaction.Transaction;
import com.sdt.banking.transaction.TransactionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerNotificationObserver implements TransactionObserver {

    private final AccountRepository accountRepository;
    private final NotificationRepository notificationRepository;

    @Override
    public void onTransactionFinished(Transaction transaction) {
        Account source = accountRepository.findById(transaction.getSourceAccountId()).orElseThrow();
        Account target = accountRepository.findById(transaction.getTargetAccountId()).orElseThrow();

        if (transaction.getStatus() == TransactionStatus.COMPLETED) {
            notify(source.getOwnerId(), "You sent " + transaction.getAmount() + " to " + target.getIban()
                    + " (fee " + transaction.getFee() + ").");
            notify(target.getOwnerId(), "You received " + transaction.getAmount() + " from " + source.getIban() + ".");
        } else if (transaction.getStatus() == TransactionStatus.REJECTED) {
            notify(source.getOwnerId(), "Transfer of " + transaction.getAmount() + " to " + target.getIban()
                    + " failed: " + transaction.getReason());
        }
    }

    private void notify(Long customerId, String message) {
        notificationRepository.save(new Notification(customerId, message));
    }
}
