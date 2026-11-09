package com.sdt.banking.repository;

import com.sdt.banking.model.BaseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public abstract class InMemoryRepository<T extends BaseEntity> {

    private final Map<Long, T> items = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    public T save(T item) {
        if (item.getId() == null) {
            item.setId(sequence.getAndIncrement());
        }
        items.put(item.getId(), item);
        return item;
    }

    public Optional<T> findById(Long id) {
        return Optional.ofNullable(items.get(id));
    }

    public List<T> findAll() {
        return new ArrayList<>(items.values());
    }
}
