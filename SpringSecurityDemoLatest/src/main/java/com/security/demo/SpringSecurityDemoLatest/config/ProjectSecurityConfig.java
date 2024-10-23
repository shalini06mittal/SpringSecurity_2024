package com.security.demo.SpringSecurityDemoLatest.config;

import com.security.demo.SpringSecurityDemoLatest.exceptionhandler.CustomBasicAuthenticationEntryPoint;
import com.security.demo.SpringSecurityDemoLatest.exceptionhandler.exceptionhandler.CustomAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("!prod")
public class ProjectSecurityConfig {

    public ProjectSecurityConfig() {
        System.out.println("COnfig loadeed");
    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//
//        UserDetails user = User.withUsername("user")
//                .password("{noop}shalini.techgatha@12345").authorities("read").build();
//
//
//        UserDetails user1 = User.withUsername("user1")
//                .password(passwordEncoder().encode("67890"))
//                .authorities("read").build();
//
//        UserDetails admin = User.withUsername("admin")
//                .password("{bcrypt}$2a$12$XtA3GDdEl2a7fjVjaehsXOMXW0TiLEQcXnSgZ2oOg2f3A5VP5jGye")
//                .authorities("admin").build();
//
//        return new InMemoryUserDetailsManager(user, admin, user1);
//    }


//    @Bean
//    public UserDetailsService userDetailsService(DataSource source){
//        System.out.println("Data Source "+source);
//        return new JdbcUserDetailsManager(source);
//    }

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
//        http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
        http.sessionManagement(smc -> smc.invalidSessionUrl("/invalidSession"))
        .requiresChannel(rcc -> rcc.anyRequest().requiresInsecure())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) ->
            requests
                    .requestMatchers("/myAccount", "/myCards", "/myLoans","/myBalance").authenticated()
                    .requestMatchers("/contact","/notices","/welcome","/register","/invalidSession").permitAll()
        );
//        http.formLogin(AbstractHttpConfigurer::disable);
        http.formLogin(withDefaults());

//        http.httpBasic(withDefaults());
        http.httpBasic((c) -> c.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        http.exceptionHandling((c) -> c.accessDeniedHandler(new CustomAccessDeniedHandler()));
        return http.build();
    }
}


