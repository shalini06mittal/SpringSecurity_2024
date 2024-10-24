package com.security.demo.SpringSecurityDemoLatest.config;

import com.security.demo.SpringSecurityDemoLatest.exceptionhandling.CustomAccessDeniedHandler;
import com.security.demo.SpringSecurityDemoLatest.exceptionhandling.CustomBasicAuthenticationEntryPoint;
import com.security.demo.SpringSecurityDemoLatest.filter.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.sql.DataSource;

import java.util.Collections;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("!prod")
public class ProjectSecurityConfigMySQL {

    @Autowired
    private JwtTokenGenerateFilter jwtTokenGenerateFilter;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();

       // http.securityContext(sc -> sc.requireExplicitSave(false));

        http.sessionManagement(smc-> smc.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.csrf(csrfConfig ->
                csrfConfig
                        .csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
                        .ignoringRequestMatchers("/contact","/register","/apiLogin")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))

                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
//                 .addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
//                .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
//                .addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class);

                .addFilterAfter(jwtTokenGenerateFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(new JwtTokenValidationFilter(), BasicAuthenticationFilter.class);

        http.cors(corscf-> corscf.configurationSource(new CorsConfigurationSource() {
                   @Override
                   public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                       CorsConfiguration corsConfiguration = new CorsConfiguration();
                       corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                       corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
                       corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
                       corsConfiguration.setAllowCredentials(true);
                       corsConfiguration.setMaxAge(3600L);
                       corsConfiguration.setExposedHeaders(List.of("Authorization"));
                       return corsConfiguration;
                   }
               }));

              // http.sessionManagement(smc -> smc.invalidSessionUrl("/invalidSession").maximumSessions(1).maxSessionsPreventsLogin(true));

               http.requiresChannel(rcc-> rcc.anyRequest().requiresInsecure()) // HTTP
                //.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/myAccount").hasAuthority("VIEWACCOUNT")
//                        .requestMatchers("/myBalance").hasAnyAuthority("VIEWBALANCE", "VIEWACCOUNT")
//                        .requestMatchers("/myLoans").hasAuthority("VIEWLOANS")
//                        .requestMatchers("/myCards").hasAuthority("VIEWCARDS")
//                        .requestMatchers("/user").authenticated()

                        .requestMatchers("/myAccount").hasRole("USER")
                        .requestMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/myLoans").hasRole("USER")
                        .requestMatchers("/myCards").hasRole("USER")
                        .requestMatchers("/user").authenticated()

                        //.requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards","/user").authenticated()
                .requestMatchers("/notices", "/contact", "/error","/register","/invalidSession","/apiLogin")
                        .permitAll());

                http.formLogin(withDefaults());
                http.httpBasic(hbc -> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
                http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));
//        http.exceptionHandling(ehc-> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()).accessDeniedPage("/denied"));
        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource) {
//
//        return new JdbcUserDetailsManager(dataSource);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * From Spring Security 6.3 version
     * @return
     */
    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }
//    @Bean
//    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
//                                                       PasswordEncoder passwordEncoder) {
//        CustomerUsernamePwdAuthenticationProvider authenticationProvider =
//                new CustomerUsernamePwdAuthenticationProvider(userDetailsService, passwordEncoder);
//        ProviderManager providerManager = new ProviderManager(authenticationProvider);
//        providerManager.setEraseCredentialsAfterAuthentication(false);
//        return  providerManager;
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
