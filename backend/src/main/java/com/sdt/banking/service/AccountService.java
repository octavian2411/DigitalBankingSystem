package com.sdt.banking.service;

import com.sdt.banking.factory.AccountFactory;
import com.sdt.banking.model.Account;
import com.sdt.banking.model.AccountStatus;
import com.sdt.banking.model.AccountType;
import com.sdt.banking.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class AccountService {

    private final Map<AccountType, AccountFactory> factories = new HashMap<>();
    private final AccountRepository accountRepository;
    private final CustomerService customerService;

    public AccountService(List<AccountFactory> factories,
                          AccountRepository accountRepository,
                          CustomerService customerService) {
        factories.forEach(factory -> this.factories.put(factory.supportedType(), factory));
        this.accountRepository = accountRepository;
        this.customerService = customerService;
    }

    public Account openAccount(Long customerId, AccountType type, BigDecimal initialDeposit) {
        customerService.get(customerId);
        AccountFactory factory = factories.get(type);
        if (factory == null) {
            throw new IllegalArgumentException("Unsupported account type: " + type);
        }
        return accountRepository.save(factory.openAccount(customerId, initialDeposit));
    }

    public Account get(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Account " + id + " not found"));
    }

    public List<Account> getForCustomer(Long customerId) {
        customerService.get(customerId);
        return accountRepository.findByOwnerId(customerId);
    }

    public Account changeStatus(Long id, AccountStatus status) {
        Account account = get(id);
        account.setStatus(status);
        return account;
    }
}
