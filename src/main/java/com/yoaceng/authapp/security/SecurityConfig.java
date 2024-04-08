package com.yoaceng.authapp.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Custom configuration class for spring security.
 *
 * @author Cayo Cutrim
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    SecurityFilter securityFilter;

    /**
     * Configures the HttpSecurity to set up web-based security for specific http requests.
     *
     * @param httpSecurity The HttpSecurity to be modified.
     * @return A SecurityFilterChain that allows configuring web based security for specific http requests.
     * @throws Exception Throws exception if an error occurs during the configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // Disables CSRF protection as it is not required for stateless authentication.
                .csrf(csrf -> csrf.disable())
                // Configures session management to stateless which is essential for REST APIs.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Configures authorization rules for HTTP requests.
                .authorizeHttpRequests(authorize -> authorize
                        // Allows public access to login and register endpoints.
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        // Requires the user to have 'ADMIN' role to access the person creation endpoint.
                        .requestMatchers(HttpMethod.POST, "/person").hasRole("ADMIN")
                        // Requires authentication for any other request.
                        .anyRequest().authenticated()
                )
                // Adds the custom securityFilter before UsernamePasswordAuthenticationFilter in the filter chain.
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Exposes the AuthenticationManager as a Bean to be used by the application.
     *
     * @param authenticationConfiguration The AuthenticationConfiguration used to build the AuthenticationManager.
     * @return The AuthenticationManager bean.
     * @throws Exception Throws exception if an error occurs during the creation.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Defines the PasswordEncoder to be used by the application for encoding passwords.
     *
     * @return A PasswordEncoder bean that uses BCrypt hashing algorithm.
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

