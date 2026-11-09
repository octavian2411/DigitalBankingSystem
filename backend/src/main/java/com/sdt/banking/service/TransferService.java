package com.sdt.banking.service;

import com.sdt.banking.model.Account;
import com.sdt.banking.notification.TransactionEventPublisher;
import com.sdt.banking.repository.TransactionRepository;
import com.sdt.banking.transaction.Transaction;
import com.sdt.banking.transaction.TransactionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final AccountService accountService;
    private final TransactionRepository transactionRepository;
    private final TransactionEventPublisher eventPublisher;

    public synchronized Transaction transfer(Long sourceAccountId, Long targetAccountId, BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount is required");
        }
        Account source = accountService.get(sourceAccountId);
        Account target = accountService.get(targetAccountId);

        BigDecimal fee = source.calculateTransferFee(amount);
        Transaction transaction = transactionRepository.save(
                new Transaction(source.getId(), target.getId(), amount, fee));

        String rejectionReason = checkTransfer(source, target, amount, fee);
        if (rejectionReason == null) {
            source.withdraw(amount.add(fee));
            target.deposit(amount);
            transaction.complete();
        } else {
            transaction.reject(rejectionReason);
        }

        eventPublisher.publish(transaction);
        return transaction;
    }

    private String checkTransfer(Account source, Account target, BigDecimal amount, BigDecimal fee) {
        if (amount.signum() <= 0) {
            return "Amount must be greater than zero";
        }
        if (source.getId().equals(target.getId())) {
            return "Source and target accounts must be different";
        }
        if (!source.isActive()) {
            return "Source account is " + source.getStatus();
        }
        if (!target.isActive()) {
            return "Target account is " + target.getStatus();
        }
        BigDecimal totalDebit = amount.add(fee);
        if (source.getBalance().compareTo(totalDebit) < 0) {
            return "Insufficient funds (needed " + totalDebit + ", available " + source.getBalance() + ")";
        }
        return null;
    }

    public List<Transaction> getHistory(Long accountId) {
        accountService.get(accountId);
        return transactionRepository.findByAccountId(accountId);
    }

    public List<Transaction> getAll(TransactionStatus status) {
        return transactionRepository.findAll().stream()
                .filter(tx -> status == null || tx.getStatus() == status)
                .toList();
    }
}
