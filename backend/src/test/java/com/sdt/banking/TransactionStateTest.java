package com.sdt.banking;

import com.sdt.banking.transaction.Transaction;
import com.sdt.banking.transaction.TransactionStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransactionStateTest {

    @Test
    void pendingTransactionCanBeCompletedOnlyOnce() {
        Transaction tx = new Transaction(1L, 2L, BigDecimal.TEN, BigDecimal.ZERO);
        assertEquals(TransactionStatus.PENDING, tx.getStatus());

        tx.complete();
        assertEquals(TransactionStatus.COMPLETED, tx.getStatus());
        assertThrows(IllegalStateException.class, () -> tx.reject("too late"));
    }

    @Test
    void rejectedTransactionKeepsReason() {
        Transaction tx = new Transaction(1L, 2L, BigDecimal.TEN, BigDecimal.ZERO);
        tx.reject("Insufficient funds");

        assertEquals(TransactionStatus.REJECTED, tx.getStatus());
        assertEquals("Insufficient funds", tx.getReason());
        assertThrows(IllegalStateException.class, tx::complete);
    }
}
