package com.security.demo.SpringSecurityDemoLatest.repo;

import com.security.demo.SpringSecurityDemoLatest.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    Optional<Customer> findByEmail(String email);
}
