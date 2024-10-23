package com.security.demo.SpringSecurityDemoLatest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("prod")
public class ProjectSecurityProdConfig {

    public ProjectSecurityProdConfig() {
        System.out.println("COnfig loadeed");
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * Only after Spring 6.3 compromised passwords
     */
    // passwords can be compromised
    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker(){
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("filter chain for security");
        http.requiresChannel(rcc -> rcc.anyRequest().requiresSecure()) // HTTPS
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) ->
            requests
                    .requestMatchers("/myAccount", "/myCards", "/myLoans","/myBalance").authenticated()
                    .requestMatchers("/contact","/notices","/welcome","/register").permitAll()
        );
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }
}


