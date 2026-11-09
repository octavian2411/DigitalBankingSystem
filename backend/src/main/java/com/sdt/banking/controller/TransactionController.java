package com.sdt.banking.controller;

import com.sdt.banking.service.TransferService;
import com.sdt.banking.transaction.Transaction;
import com.sdt.banking.transaction.TransactionStatus;
import com.sdt.banking.dto.TransferRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TransactionController {

    private final TransferService transferService;

    @PostMapping("/transfers")
    public Transaction transfer(@RequestBody TransferRequest request) {
        return transferService.transfer(request.sourceAccountId(), request.targetAccountId(), request.amount());
    }

    @GetMapping("/admin/transactions")
    public List<Transaction> getAll(@RequestParam(required = false) TransactionStatus status) {
        return transferService.getAll(status);
    }
}
