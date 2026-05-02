package org.rocs.vra.exception.domain;

/**
 * Exception thrown when an email address is not found in the system.
 */
public class EmailNotFoundException extends Exception{
    public EmailNotFoundException(String message) {
        super(message);
    }
}
