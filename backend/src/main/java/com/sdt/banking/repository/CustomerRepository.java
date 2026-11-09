package com.sdt.banking.repository;

import com.sdt.banking.model.Customer;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepository extends InMemoryRepository<Customer> {
}
