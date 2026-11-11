package com.sdt.banking.service;

import com.sdt.banking.model.Customer;
import com.sdt.banking.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer update(Long id, String name, String email) {
        Customer customer = get(id);
        customer.update(name, email);
        return customer;
    }

    public Customer get(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Customer " + id + " not found"));
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }
}
