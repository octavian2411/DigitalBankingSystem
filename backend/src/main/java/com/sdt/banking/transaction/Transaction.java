package com.sdt.banking.transaction;

import com.sdt.banking.model.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@RequiredArgsConstructor
public class Transaction extends BaseEntity {

    private final Long sourceAccountId;
    private final Long targetAccountId;
    private final BigDecimal amount;
    private final BigDecimal fee;
    private final Instant createdAt = Instant.now();
    @Getter(AccessLevel.NONE)
    private TransactionState state = new PendingState();
    @Setter(AccessLevel.PACKAGE)
    private String reason;

    public void complete() {
        state.complete(this);
    }

    public void reject(String reason) {
        state.reject(this, reason);
    }

    public TransactionStatus getStatus() {
        return state.getStatus();
    }

    void changeState(TransactionState state) {
        this.state = state;
    }
}
