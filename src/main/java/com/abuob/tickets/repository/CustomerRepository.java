package com.abuob.tickets.repository;

import com.abuob.tickets.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Optional<Customer> findById(Long id);
}
