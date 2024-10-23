package com.security.demo.SpringSecurityDemoLatest.exceptionhandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.time.LocalDateTime;

public class CustomBasicAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        response.setHeader("bank-error-reason",  " Auth failed");
//        response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());

        // Populate dynamic values

        System.out.println("Message --> "+ authException.getMessage());
        LocalDateTime currentTimeStamp = LocalDateTime.now();
        String message = (authException != null && authException.getMessage() != null) ?
                "My Custom Message "+authException.getMessage()
                : "Unauthorized";
        String path = request.getRequestURI();
        response.setHeader("bank-error-reason", "Authentication failed");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        // Construct the JSON response
        String jsonResponse =
                String.format("{\"timestamp\": \"%s\", \"status\": %d, \"error\": \"%s\", \"message\": \"%s\", \"path\": \"%s\"}",
                        currentTimeStamp, HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), message, path);

        response.getWriter().write(jsonResponse);

    }
}
