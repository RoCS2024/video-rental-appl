package org.rocs.vra.utils.security.jwt.filter.authentication.forbidden;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.rocs.vra.domain.http.response.HttpResponse;
import org.rocs.vra.utils.security.constant.SecurityConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Triggers when authentication fails.
 * Custom authentication entry point that handles AuthenticationException by returning
 * a structured JSON error response with HTTP status 403 Forbidden.
 * */
@Component
public class AuthenticationEntryPoint extends Http403ForbiddenEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        // Create a http response with forbidden status
        HttpResponse httpResponse = new HttpResponse(HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN,
                HttpStatus.FORBIDDEN.getReasonPhrase().toUpperCase(),
                SecurityConstant.FORBIDDEN_MESSAGE);
        // set the content type of the response to JSON
        response.setContentType(APPLICATION_JSON_VALUE);
        // set the status of the response to forbidden
        response.setStatus(HttpStatus.FORBIDDEN.value());
        // obtains the output stream from the HttpServletResponse object.
        OutputStream outputStream = response.getOutputStream();
        // creates a new Jackson ObjectMapper instance.
        ObjectMapper mapper = new ObjectMapper();
        // serializes the httpResponse object into JSON and writes it to the outputStream.
        mapper.writeValue(outputStream, httpResponse);
        // flushes the output stream, forcing any buffered data to be written out to the HTTP response.
        outputStream.flush();
    }
}
