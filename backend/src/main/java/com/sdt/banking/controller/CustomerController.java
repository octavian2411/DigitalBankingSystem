package com.sdt.banking.controller;

import com.sdt.banking.model.Account;
import com.sdt.banking.model.Customer;
import com.sdt.banking.model.Notification;
import com.sdt.banking.repository.NotificationRepository;
import com.sdt.banking.service.AccountService;
import com.sdt.banking.service.CustomerService;
import com.sdt.banking.dto.CustomerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final AccountService accountService;
    private final NotificationRepository notificationRepository;

    @GetMapping
    public List<Customer> getAll() {
        return customerService.getAll();
    }

    @GetMapping("/{id}")
    public Customer get(@PathVariable Long id) {
        return customerService.get(id);
    }

    @PutMapping("/{id}")
    public Customer update(@PathVariable Long id, @RequestBody CustomerRequest request) {
        return customerService.update(id, request.name(), request.email());
    }

    @GetMapping("/{id}/accounts")
    public List<Account> getAccounts(@PathVariable Long id) {
        return accountService.getForCustomer(id);
    }

    @GetMapping("/{id}/notifications")
    public List<Notification> getNotifications(@PathVariable Long id) {
        customerService.get(id);
        return notificationRepository.findByCustomerId(id);
    }
}
