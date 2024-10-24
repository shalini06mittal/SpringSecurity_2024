package com.security.demo.SpringSecurityDemoLatest.filter;

import com.security.demo.SpringSecurityDemoLatest.constants.ApplicationConstants;
import com.security.demo.SpringSecurityDemoLatest.jwt.GenerateToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtTokenGenerateFilter extends OncePerRequestFilter {

    @Autowired
    private GenerateToken generateToken;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("Generate token filter invoked after authentication");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(null != authentication) {
            log.info("User " + authentication.getName() + " is successfully authenticated and "
                    + "has the authorities " + authentication.getAuthorities().toString());

            String token = generateToken.generateToken(authentication);

            response.setHeader(ApplicationConstants.JWT_HEADER, token);
        }
        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/user");
    }
}
