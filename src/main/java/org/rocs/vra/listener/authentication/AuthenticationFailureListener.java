package org.rocs.vra.listener.authentication;

import org.rocs.vra.service.login.attempt.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

/**
 * Spring component that listens to authentication failure events and tracks failed login attempts.
 * When a user provides bad credentials, this listener extracts the username from the event
 * and delegates to LoginAttemptService to increment the failure counter for that user.
 */
@Component
public class AuthenticationFailureListener {

    /**
     * The login attempt service.
     */
    public LoginAttemptService loginAttemptService;

    /**
     * Constructs the listener with the required login attempt service.
     */
    @Autowired
    public AuthenticationFailureListener(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    /**
     * Handles AuthenticationFailureBadCredentialsEvent events.
     * Extracts the username from the authentication principal and, if it is a String,
     * calls LoginAttemptService.addUserToLoginAttemptCache(String) to register the failed attempt.
     */
    @EventListener
    public void onAuthenticationFailure(AuthenticationFailureBadCredentialsEvent event) {
        Object principal = event.getAuthentication().getPrincipal();
        if(principal instanceof String) {
            String username = (String) event.getAuthentication().getPrincipal();
            loginAttemptService.addUserToLoginAttemptCache(username);
        }
    }

}
