package com.group2.library_management.security;

import org.springframework.security.core.Authentication;

/**
 * An interface to abstract away the details of accessing the SecurityContext.
 */
public interface IAuthenticationFacade {
    
    /**
     * Gets the current authentication object from the security context.
     * @return The current Authentication object.
     */
    Authentication getAuthentication();
}
