package com.sdt.banking;

import com.sdt.banking.model.Account;
import com.sdt.banking.model.AccountStatus;
import com.sdt.banking.model.AccountType;
import com.sdt.banking.model.Customer;
import com.sdt.banking.repository.NotificationRepository;
import com.sdt.banking.service.AccountService;
import com.sdt.banking.service.CustomerService;
import com.sdt.banking.service.TransferService;
import com.sdt.banking.transaction.Transaction;
import com.sdt.banking.transaction.TransactionStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class TransferServiceTest {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransferService transferService;
    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    void savingsTransferChargesFeeAndNotifiesBothCustomers() {
        Customer sender = customerService.create("Sender", "s@test.com");
        Customer receiver = customerService.create("Receiver", "r@test.com");
        Account savings = accountService.openAccount(sender.getId(), AccountType.SAVINGS, new BigDecimal("1000.00"));
        Account checking = accountService.openAccount(receiver.getId(), AccountType.CHECKING, BigDecimal.ZERO);

        Transaction tx = transferService.transfer(savings.getId(), checking.getId(), new BigDecimal("100.00"));

        assertEquals(TransactionStatus.COMPLETED, tx.getStatus());
        assertEquals(new BigDecimal("1.00"), tx.getFee());
        assertEquals(new BigDecimal("899.00"), savings.getBalance());
        assertEquals(new BigDecimal("100.00"), checking.getBalance());
        assertFalse(notificationRepository.findByCustomerId(sender.getId()).isEmpty());
        assertFalse(notificationRepository.findByCustomerId(receiver.getId()).isEmpty());
    }

    @Test
    void transferIsRejectedWhenFundsAreMissing() {
        Customer customer = customerService.create("Poor", "p@test.com");
        Account a = accountService.openAccount(customer.getId(), AccountType.CHECKING, new BigDecimal("50.00"));
        Account b = accountService.openAccount(customer.getId(), AccountType.CHECKING, BigDecimal.ZERO);

        Transaction tx = transferService.transfer(a.getId(), b.getId(), new BigDecimal("80.00"));

        assertEquals(TransactionStatus.REJECTED, tx.getStatus());
        assertEquals(new BigDecimal("50.00"), a.getBalance());
    }

    @Test
    void transferIsRejectedWhenAccountIsFrozen() {
        Customer customer = customerService.create("Frozen", "f@test.com");
        Account a = accountService.openAccount(customer.getId(), AccountType.CHECKING, new BigDecimal("50.00"));
        Account b = accountService.openAccount(customer.getId(), AccountType.CHECKING, BigDecimal.ZERO);
        accountService.changeStatus(a.getId(), AccountStatus.FROZEN);

        Transaction tx = transferService.transfer(a.getId(), b.getId(), new BigDecimal("10.00"));

        assertEquals(TransactionStatus.REJECTED, tx.getStatus());
    }
}
