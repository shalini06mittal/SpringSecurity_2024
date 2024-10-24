package com.security.demo.SpringSecurityDemoLatest.jwt;

import com.security.demo.SpringSecurityDemoLatest.constants.ApplicationConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenerateToken {

    private final Environment environment;

    public String generateToken(Authentication authentication) {

       // String secret = ApplicationConstants.JWT_SECRET_DEFAULT_VALUE;
        String secret = environment.getProperty(ApplicationConstants.JWT_SECRET_KEY,
                ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

       String jwt = Jwts.builder()
               .issuer("spring-security-demo")
               .claim("username",authentication.getName())
               .claim("authorities",authentication.getAuthorities()
                       .stream().map(GrantedAuthority::getAuthority)
                       .collect(Collectors.joining(",")))
               .subject("JWT Token for authenticatio")
               .issuedAt(new Date())
               .expiration(new Date((new Date()).getTime() + 300000))
               .signWith(secretKey).compact();

        return jwt;
    }
}
