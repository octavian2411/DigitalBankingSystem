package com.sdt.banking.repository;

import com.sdt.banking.model.Notification;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;

@Repository
public class NotificationRepository extends InMemoryRepository<Notification> {

    public List<Notification> findByCustomerId(Long customerId) {
        return findAll().stream()
                .filter(n -> n.getCustomerId().equals(customerId))
                .sorted(Comparator.comparing(Notification::getId).reversed())
                .toList();
    }
}
