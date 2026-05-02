package org.rocs.vra.exception.domain;

/**
 * Exception thrown when attempting to register a user with an email that already exists.
 */
public class EmailExistsException extends Exception {
    public EmailExistsException(String message) {
        super(message);
    }
}
