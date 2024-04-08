package com.yoaceng.authapp.security;


import com.yoaceng.authapp.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * This custom security filter extends OncePerRequestFilter from Spring Security to ensure that
 * it is applied once per request.
 *
 * @author Cayo Cutrim
 */
@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    /**
     * The core method of the filter that is automatically called by Spring Security for each request.
     *
     * @param request The received HTTP request.
     * @param response The HTTP response to be sent.
     * @param filterChain The chain of filters of Spring Security.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Retrieves the JWT token from the request header.
        var token = this.recoverToken(request);

        // If a token was retrieved, tries to validate and authenticate the user based on the token.
        if (token != null){
            // Validates the token and retrieves the user's login if the token is valid.
            var login = tokenService.validateToken(token);
            UserDetails user = userRepository.findByLogin(login);

            // Creates an authentication object with the user's details and authorities.
            var authentication = new UsernamePasswordAuthenticationToken(user, null , user.getAuthorities());
            // Sets the authentication object in the Spring Security's security context.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Continues the filter chain, allowing the request to proceed to the next filter or controller.
        filterChain.doFilter(request, response);
    }

    /**
     * Helper method to retrieve the JWT token from the "Authorization" header of the request.
     *
     * @param request The HTTP request from which the token will be retrieved.
     * @return The JWT token without the "Bearer " prefix, or null if the header is not present.
     */
    private String recoverToken(HttpServletRequest request){
        // Retrieves the "Authorization" header from the request.
        var authHeader = request.getHeader("Authorization");
        // If the header is present, removes the "Bearer " prefix and returns the pure token.
        if(authHeader == null) return null;

        return authHeader.replace("Bearer ", "");
    }
}

