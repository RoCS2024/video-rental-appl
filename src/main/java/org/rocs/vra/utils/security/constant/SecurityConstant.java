package org.rocs.vra.utils.security.constant;

/**
 * Central repository of security-related constants used throughout the application.
 */
public class SecurityConstant {
    /** JWT expiration time in milliseconds. */
    public static final long EXPIRATION_TIME = 432000000; //5 days in milliseconds

    /** The prefix to be prepended to JWT tokens in the Authorization header. */
    public static final String TOKEN_PREFIX = "Bearer ";

    /** The name of the HTTP header used to return or verify the JWT token. */
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";

    /** Error message displayed when a JWT token cannot be verified. */
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";

    /** The issuer identifier for JWT tokens. */
    public static final String ROCS = "RoCS";

    /** The application name or administration title. */
    public static final String ROCS_ADMINISTRATION = "Video Rental Self Service";

    /** The claim key for storing user authorities inside the JWT token. */
    public static final String AUTHORITIES = "authorities";

    /** Message displayed when an unauthenticated user attempts to access a protected resource. */
    public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";

    /** Message displayed when an authenticated user lacks sufficient permissions. */
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";

    /** The HTTP method name for preflight CORS requests. */
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";

    /** Array of URL paths that are publicly accessible without authentication. */
    public static final String[] PUBLIC_URLS = {"/user/login", "/user/register"};
}
