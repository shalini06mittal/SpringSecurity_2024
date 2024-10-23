package com.security.demo.SpringSecurityDemoLatest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeControlller {

    public WelcomeControlller() {
        System.out.println("welcome const");
    }

    @GetMapping("/welcome")
    public String sayWelcome(){
        return "Welcome to spring security tutorial";
    }

    @GetMapping("/invalidSession")
    public String invalidSession(){
        return "Session Timed out. Please login again";
    }
}
