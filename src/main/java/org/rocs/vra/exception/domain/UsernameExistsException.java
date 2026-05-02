package org.rocs.vra.exception.domain;

/**
 * Exception thrown when attempting to register a user with an username that already exists.
 */
public class UsernameExistsException extends Exception {
    public UsernameExistsException(String message) {
        super(message);
    }
}
