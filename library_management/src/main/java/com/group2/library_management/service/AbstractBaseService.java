package com.group2.library_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

import com.group2.library_management.entity.User;
import com.group2.library_management.exception.InvalidPrincipalTypeException;
import com.group2.library_management.exception.UserNotFoundException;
import com.group2.library_management.repository.UserRepository;
import com.group2.library_management.security.IAuthenticationFacade;

public abstract class AbstractBaseService {
    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieves the currently authenticated User entity from the database.
     * @return The current User entity.
     * @throws UserNotFoundException if the user cannot be found.
     * @throws InvalidPrincipalTypeException if the principal is not of type Jwt.
     */
    protected User getCurrentUser() {
        Authentication authentication = authenticationFacade.getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new InvalidPrincipalTypeException();
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof Jwt)) {
            throw new InvalidPrincipalTypeException();
        }

        Jwt jwtPrincipal = (Jwt) principal;
        String email = jwtPrincipal.getSubject();
        
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }
}
