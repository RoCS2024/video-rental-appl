package org.rocs.vra.exception;

import com.auth0.jwt.exceptions.TokenExpiredException;
import org.rocs.vra.domain.http.response.HttpResponse;
import org.rocs.vra.exception.domain.EmailExistsException;
import org.rocs.vra.exception.domain.EmailNotFoundException;
import org.rocs.vra.exception.domain.UserNotFoundException;
import org.rocs.vra.exception.domain.UsernameExistsException;
import jakarta.persistence.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;

import static org.rocs.vra.exception.constant.ExceptionConstant.*;
import static org.springframework.http.HttpStatus.*;

/**
 * Global exception handler for REST controllers.
 * Provides centralized exception handling and error responses across the application.
 */
@RestControllerAdvice
public class ExceptionHandling implements ErrorController {

    /** Logger for recording exception details. */
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * Handles DisabledException (account disabled).
     * @return 400 Bad Request with ACCOUNT_DISABLED message
     */
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<HttpResponse> accountDisabledException() {
        return createHttpResponse(BAD_REQUEST, ACCOUNT_DISABLED);
    }

    /**
     * Handles BadCredentialsException (invalid username/password).
     * @return 400 Bad Request with INCORRECT_CREDENTIALS message
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpResponse> badCredentialsException() {
        return createHttpResponse(BAD_REQUEST, INCORRECT_CREDENTIALS);
    }

    /**
     * Handles AccessDeniedException (insufficient permissions).
     * @return 403 Forbidden with NOT_ENOUGH_PERMISSION message
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpResponse> accessDeniedException() {
        return createHttpResponse(FORBIDDEN, NOT_ENOUGH_PERMISSION);
    }

    /**
     * Handles LockedException (account locked).
     * @return 401 Unauthorized with ACCOUNT_LOCKED message
     */
    @ExceptionHandler(LockedException.class)
    public ResponseEntity<HttpResponse> lockedException() {
        return createHttpResponse(UNAUTHORIZED, ACCOUNT_LOCKED);
    }

    /**
     * Handles TokenExpiredException (JWT expired).
     * @param exception the thrown exception
     * @return 401 Unauthorized with the exception's message
     */
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<HttpResponse> tokenExpiredException(TokenExpiredException exception) {
        return createHttpResponse(UNAUTHORIZED, exception.getMessage());
    }

    /**
     * Handles EmailExistsException (duplicate email).
     * @param exception the thrown exception
     * @return 400 Bad Request with the exception's message
     */
    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<HttpResponse> emailExistException(EmailExistsException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    /**
     * Handles UsernameExistsException (duplicate username).
     * @param exception the thrown exception
     * @return 400 Bad Request with the exception's message
     */
    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<HttpResponse> usernameExistException(UsernameExistsException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    /**
     * Handles EmailNotFoundException (email not found).
     * @param exception the thrown exception
     * @return 400 Bad Request with the exception's message
     */
    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<HttpResponse> emailNotFoundException(EmailNotFoundException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    /**
     * Handles UserNotFoundException (user not found).
     * @param exception the thrown exception
     * @return 400 Bad Request with the exception's message
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<HttpResponse> userNotFoundException(UserNotFoundException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    /**
     * Handles generic Exception (unexpected server error).
     * Logs the error and returns 500 Internal Server Error.
     * @param exception the thrown exception
     * @return 500 Internal Server Error with generic message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> internalServerErrorException(Exception exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
    }

    /**
     * Handles NoResultException (JPA query returned no result).
     * Logs the error and returns 404 Not Found.
     * @param exception the thrown exception
     * @return 404 Not Found with the exception's message
     */
    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<HttpResponse> notFoundException(NoResultException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(NOT_FOUND, exception.getMessage());
    }

    /**
     * Handles IOException (file processing error).
     * Logs the error and returns 500 Internal Server Error.
     * @param exception the thrown exception
     * @return 500 Internal Server Error with ERROR_PROCESSING_FILE message
     */
    @ExceptionHandler(IOException.class)
    public ResponseEntity<HttpResponse> iOException(IOException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(INTERNAL_SERVER_ERROR, ERROR_PROCESSING_FILE);
    }

    /**
     * Handles NoHandlerFoundException (no mapping for the request URL).
     * @param e the thrown exception
     * @return 400 Bad Request with "There is no mapping for this URL"
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<HttpResponse> noHandlerFoundException(NoHandlerFoundException e) {
        return createHttpResponse(BAD_REQUEST, "There is no mapping for this URL");
    }

    /**
     * Builds a ResponseEntity with standard HTTP response structure.
     * @param httpStatus the HTTP status enum
     * @param message    the custom message
     * @return ResponseEntity containing an HttpResponse object
     */
    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus,
                httpStatus.getReasonPhrase().toUpperCase(), message.toUpperCase()), httpStatus);
    }

    /**
     * Handles requests to the default error path (e.g., /error).
     * @return 404 Not Found with "There is no mapping for this URL"
     */
    @RequestMapping(ERROR_PATH)
    public ResponseEntity<HttpResponse> notFound404() {
        return createHttpResponse(NOT_FOUND, "There is no mapping for this URL");
    }

    /**
     * Returns the error path (required by ErrorController).
     * @return the ERROR_PATH constant
     */
    public String getErrorPath() {
        return ERROR_PATH;
    }

}
