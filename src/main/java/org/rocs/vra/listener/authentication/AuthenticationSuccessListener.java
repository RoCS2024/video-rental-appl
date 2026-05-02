package org.rocs.vra.listener.authentication;

import org.rocs.vra.domain.user.User;
import org.rocs.vra.service.login.attempt.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * Spring component that listens to successful authentication events and clears
 * the failed attempt counter for the authenticated user.
 * When a user successfully logs in, this listener extracts the username from
 * the authenticated User principal and delegates to
 * LoginAttemptService to evict that user from the login attempt cache,
 * resetting their failed attempt count.
 */
@Component
public class AuthenticationSuccessListener {

    /**
     * The login attempt service.
     */
    private LoginAttemptService loginAttemptService;

    /**
     * Constructs the success listener with the required login attempt service.
     */
    @Autowired
    public AuthenticationSuccessListener(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    /**
     * Handles AuthenticationSuccessEvent events.
     * Extracts the principal from the authentication object. If the principal
     * is an instance of User, the username is obtained and passed to
     * LoginAttemptService.evictUserFromLoginAttemptCache(String) to
     * remove the user from the failed attempts cache.
     */
    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        Object principal = event.getAuthentication().getPrincipal();
        if(principal instanceof User) {
            User user = (User) event.getAuthentication().getPrincipal();
            loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
        }
    }
}
