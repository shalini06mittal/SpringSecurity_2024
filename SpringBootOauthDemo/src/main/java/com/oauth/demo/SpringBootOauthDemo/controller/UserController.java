package com.oauth.demo.SpringBootOauthDemo.controller;



import com.oauth.demo.SpringBootOauthDemo.model.Customer;
import com.oauth.demo.SpringBootOauthDemo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final CustomerRepository customerRepository;


    //happy@example.com : EazyBytes@54321
    @RequestMapping("/user")
    public Customer getUserDetailsAfterLogin(Authentication authentication) {
        System.out.println("Name "+authentication.getName());
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(authentication.getName());
        return optionalCustomer.orElse(null);
    }
}
