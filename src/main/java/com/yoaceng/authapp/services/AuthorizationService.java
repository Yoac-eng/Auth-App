package com.yoaceng.authapp.services;

import com.yoaceng.authapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Marks this class as a Service component, making it a candidate for Spring's component scanning to detect and
// register it as a bean in the application context. It implements UserDetailsService for user retrieval logic.
@Service
public class AuthorizationService implements UserDetailsService {

    // Injects an instance of UserRepository to interact with the database for user-related operations.
    @Autowired
    UserRepository repository;

    /**
     * Loads a user's data given their username. This method is used by Spring Security
     * during the authentication process.
     *
     * @param username The username identifying the user whose data is to be loaded.
     * @return UserDetails A UserDetails instance representing the user's information.
     * @throws UsernameNotFoundException If the user could not be found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // The returned object is expected to be a UserDetails instance,
        // which Spring Security uses for comparing the credentials and authorities during the authentication process.
        return repository.findByLogin(username);
    }
}
