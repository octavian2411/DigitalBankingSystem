package com.sdt.banking.repository;

import com.sdt.banking.transaction.Transaction;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;

@Repository
public class TransactionRepository extends InMemoryRepository<Transaction> {

    public List<Transaction> findByAccountId(Long accountId) {
        return findAll().stream()
                .filter(tx -> tx.getSourceAccountId().equals(accountId) || tx.getTargetAccountId().equals(accountId))
                .sorted(Comparator.comparing(Transaction::getId).reversed())
                .toList();
    }
}
