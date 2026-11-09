package com.sdt.banking.dto;

import com.sdt.banking.model.AccountType;

import java.math.BigDecimal;

public record OpenAccountRequest(Long customerId, AccountType type, BigDecimal initialDeposit) {
}
