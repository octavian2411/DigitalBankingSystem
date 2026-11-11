package com.sdt.banking.controller;

import com.sdt.banking.model.Account;
import com.sdt.banking.service.AccountService;
import com.sdt.banking.service.TransferService;
import com.sdt.banking.transaction.Transaction;
import com.sdt.banking.dto.OpenAccountRequest;
import com.sdt.banking.dto.StatusRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final TransferService transferService;

    @PostMapping
    public Account open(@RequestBody OpenAccountRequest request) {
        return accountService.openAccount(request.customerId(), request.type(), request.initialDeposit());
    }

    @GetMapping("/{id}")
    public Account get(@PathVariable Long id) {
        return accountService.get(id);
    }

    @PatchMapping("/{id}/status")
    public Account changeStatus(@PathVariable Long id, @RequestBody StatusRequest request) {
        return accountService.changeStatus(id, request.status());
    }

    @GetMapping("/{id}/transactions")
    public List<Transaction> getHistory(@PathVariable Long id) {
        return transferService.getHistory(id);
    }
}
