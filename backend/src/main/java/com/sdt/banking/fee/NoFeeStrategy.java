package com.sdt.banking.fee;

import java.math.BigDecimal;

public class NoFeeStrategy implements FeeStrategy {

    @Override
    public BigDecimal calculateFee(BigDecimal amount) {
        return BigDecimal.ZERO;
    }
}
