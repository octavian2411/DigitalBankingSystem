package com.sdt.banking.notification;

import com.sdt.banking.transaction.Transaction;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class AuditLogObserver implements TransactionObserver {

    @Override
    public void onTransactionFinished(Transaction transaction) {
        log.info("[AUDIT] tx={} from={} to={} amount={} fee={} status={} reason={}",
                transaction.getId(), transaction.getSourceAccountId(), transaction.getTargetAccountId(),
                transaction.getAmount(), transaction.getFee(), transaction.getStatus(), transaction.getReason());
    }
}
