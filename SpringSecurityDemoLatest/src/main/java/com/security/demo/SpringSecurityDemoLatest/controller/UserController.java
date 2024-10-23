package com.security.demo.SpringSecurityDemoLatest.controller;

import com.security.demo.SpringSecurityDemoLatest.entity.Customer;
import com.security.demo.SpringSecurityDemoLatest.repo.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * {
     *     "email":"dummy@example.com",
     *     "pwd":"dummy.dummy@12345",
     *     "role":"user"
     * }
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@RequestBody Customer customer){

        customer.setPwd(passwordEncoder.encode(customer.getPwd()));
        customerRepository.save(customer);

        return ResponseEntity.ok("User registered successfully");
    }

}
