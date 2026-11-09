package com.sdt.banking.service;

import com.sdt.banking.factory.AccountCreator;
import com.sdt.banking.model.Account;
import com.sdt.banking.model.AccountStatus;
import com.sdt.banking.model.AccountType;
import com.sdt.banking.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class AccountService {

    private final Map<AccountType, AccountCreator> creators = new EnumMap<>(AccountType.class);
    private final AccountRepository accountRepository;
    private final CustomerService customerService;

    public AccountService(List<AccountCreator> creators,
                          AccountRepository accountRepository,
                          CustomerService customerService) {
        creators.forEach(creator -> this.creators.put(creator.supportedType(), creator));
        this.accountRepository = accountRepository;
        this.customerService = customerService;
    }

    public Account openAccount(Long customerId, AccountType type, BigDecimal initialDeposit) {
        customerService.get(customerId);
        AccountCreator creator = creators.get(type);
        if (creator == null) {
            throw new IllegalArgumentException("Unsupported account type: " + type);
        }
        return accountRepository.save(creator.openAccount(customerId, initialDeposit));
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
