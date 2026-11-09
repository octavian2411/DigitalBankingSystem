package com.sdt.banking.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Getter
@RequiredArgsConstructor
public class Notification extends BaseEntity {

    private final Long customerId;
    private final String message;
    private final Instant createdAt = Instant.now();
}
