package com.security.demo.SpringSecurityDemoLatest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 1. SecurityProperties -> spring security specific properties
 * 2. jasypt encryptor
 * 3. UsernamePasswordAuthenticationFilter : doFilter(HttpServletRequest, HttpServletResponse, FilterChain)
 */
@SpringBootApplication
public class SpringSecurityDemoLatestApplication {

	public static void main(String[] args) {


		SpringApplication.run(SpringSecurityDemoLatestApplication.class, args);
	}

}
