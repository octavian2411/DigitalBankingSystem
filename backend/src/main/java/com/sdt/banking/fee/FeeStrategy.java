package com.sdt.banking.fee;

import java.math.BigDecimal;

public interface FeeStrategy {

    BigDecimal calculateFee(BigDecimal amount);
}
