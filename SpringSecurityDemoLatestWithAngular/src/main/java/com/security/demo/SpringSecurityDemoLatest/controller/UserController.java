package com.security.demo.SpringSecurityDemoLatest.controller;


import com.security.demo.SpringSecurityDemoLatest.constants.ApplicationConstants;
import com.security.demo.SpringSecurityDemoLatest.jwt.GenerateToken;
import com.security.demo.SpringSecurityDemoLatest.model.Customer;
import com.security.demo.SpringSecurityDemoLatest.model.LoginRequestDTO;
import com.security.demo.SpringSecurityDemoLatest.model.LoginResponseDTO;
import com.security.demo.SpringSecurityDemoLatest.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
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
    private final PasswordEncoder passwordEncoder;


    private final AuthenticationManager authenticationManager;
    private final Environment env;
    private final GenerateToken generateToken;

    @PostMapping("/apiLogin")
    public ResponseEntity<LoginResponseDTO> apiLogin (@RequestBody LoginRequestDTO loginRequest) {
        String jwt = "";
        Authentication authenticationResponse =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(),loginRequest.password()));
        if(null != authenticationResponse && authenticationResponse.isAuthenticated()) {
            if (null != env) {
                jwt = generateToken.generateToken(authenticationResponse);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).header(ApplicationConstants.JWT_HEADER,jwt)
                .body(new LoginResponseDTO(HttpStatus.OK.getReasonPhrase(), jwt));
    }

    /**
     * {
     *     "name":"John Doe",
     *     "email":"john@example.com",
     *     "pwd":"dummy.dummy@12345",
     *     "role":"user",
     *     "mobileNumber":"1212121212"
     * }
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer) {

        try {
            String hashPwd = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(hashPwd);
            customer.setCreateDt(new Date(System.currentTimeMillis()));
            Customer savedCustomer = customerRepository.save(customer);

            if (savedCustomer.getId() > 0) {
                return ResponseEntity.status(HttpStatus.CREATED).
                        body("Given user details are successfully registered");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                        body("User registration failed");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body("An exception occurred: " + ex.getMessage());
        }
    }

    @RequestMapping("/user")
    public Customer getUserDetailsAfterLogin(Authentication authentication) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(authentication.getName());
        return optionalCustomer.orElse(null);
    }

}
