package org.rocs.vra.domain.http.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * A generic HTTP response wrapper used for standardizing API responses.
 */
@Data
@AllArgsConstructor
public class HttpResponse {

    /**
     * The numeric HTTP status code (e.g., 200, 400, 401, 500).
     */
    private int httpStatusCode;

    /**
     * The HTTP status enum representation corresponding to the status code.
     */
    private HttpStatus httpStatus;

    /**
     * The standard HTTP reason phrase associated with the status code
     * (e.g., "OK", "Bad Request", "Unauthorized").
     */
    private String reason;

    /**
     * A custom, human-readable message providing additional details about the response.
     */
    private String message;
}
