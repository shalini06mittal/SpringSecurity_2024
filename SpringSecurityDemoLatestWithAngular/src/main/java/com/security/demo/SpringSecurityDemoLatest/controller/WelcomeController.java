package com.security.demo.SpringSecurityDemoLatest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping("/welcome")
    public  String sayWelcome () {
        return "Welcome to Spring Application with security";
    }

    @GetMapping("/invalidSession")
    public  String invalidSession () {
        return "Session time out";
    }

    
}
