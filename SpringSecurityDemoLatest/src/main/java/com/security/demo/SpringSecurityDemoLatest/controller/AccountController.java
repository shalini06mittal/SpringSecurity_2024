package com.security.demo.SpringSecurityDemoLatest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AccountController {

    @GetMapping("/myAccount")
    public  String getAccountDetails (Principal p) {
        return "Here are the account details from the DB "+p.getName();
    }

}
