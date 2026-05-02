package org.rocs.vra.exception.constant;

/**
 * Constants for exception messages and error paths.
 */
public class ExceptionConstant {

    /** Account locked due to security policy. */
    public static final String ACCOUNT_LOCKED = "Your account has been locked. Please contact administration";

    /** Template for HTTP method not allowed errors. Use with String.format(). */
    public static final String METHOD_IS_NOT_ALLOWED = "This request method is not allowed on this endpoint. Please send a '%s' request";

    /** Generic internal server error message. */
    public static final String INTERNAL_SERVER_ERROR_MSG = "An error occurred while processing the request";

    /** Invalid username or password. */
    public static final String INCORRECT_CREDENTIALS = "Username / password incorrect. Please try again";

    /** Account administratively disabled. */
    public static final String ACCOUNT_DISABLED = "Your account has been disabled. If this is an error, please contact administration";

    /** File processing error. */
    public static final String ERROR_PROCESSING_FILE = "Error occurred while processing file";

    /** Insufficient permissions for the requested resource. */
    public static final String NOT_ENOUGH_PERMISSION = "You do not have enough permission";

    /** Standard error endpoint path. */
    public static final String ERROR_PATH = "/error";
}