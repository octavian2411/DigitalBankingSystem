package com.sdt.banking.fee;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RequiredArgsConstructor
public class PercentageFeeStrategy implements FeeStrategy {

    private final BigDecimal rate;

    @Override
    public BigDecimal calculateFee(BigDecimal amount) {
        return amount.multiply(rate).setScale(2, RoundingMode.HALF_UP);
    }
}
