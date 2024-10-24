package com.oauth.demo.SpringBootOauthDemo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
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

    @GetMapping("/token")
    public CsrfToken csrfToken (HttpServletRequest request) {
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        return token;

    }

    
}
