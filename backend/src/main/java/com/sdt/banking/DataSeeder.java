package com.sdt.banking;

import com.sdt.banking.model.AccountType;
import com.sdt.banking.model.Customer;
import com.sdt.banking.repository.CustomerRepository;
import com.sdt.banking.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final AccountService accountService;

    @Override
    public void run(String... args) {
        Customer alice = customerRepository.save(new Customer("Alex Popescu", "alex@example.com"));
        Customer bob = customerRepository.save(new Customer("Bob Ionescu", "bob@example.com"));

        accountService.openAccount(alice.getId(), AccountType.CHECKING, new BigDecimal("1000.00"));
        accountService.openAccount(alice.getId(), AccountType.SAVINGS, new BigDecimal("5000.00"));
        accountService.openAccount(bob.getId(), AccountType.CHECKING, new BigDecimal("500.00"));
    }
}
