package com.sdt.banking.repository;

import com.sdt.banking.model.Account;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountRepository extends InMemoryRepository<Account> {

    public List<Account> findByOwnerId(Long ownerId) {
        return findAll().stream()
                .filter(account -> account.getOwnerId().equals(ownerId))
                .toList();
    }
}
