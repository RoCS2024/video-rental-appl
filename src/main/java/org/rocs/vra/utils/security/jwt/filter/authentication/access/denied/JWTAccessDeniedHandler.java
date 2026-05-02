package org.rocs.vra.utils.security.jwt.filter.authentication.access.denied;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.rocs.vra.domain.http.response.HttpResponse;
import org.rocs.vra.utils.security.constant.SecurityConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Custom Spring Security component that handles AccessDeniedException – which occurs when an authenticated user
 * tries to access a resource they do not have permission for (HTTP 403 Forbidden).
 */
@Component
public class JWTAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException {
        // Create a http response with unauthorized status
        HttpResponse httpResponse = new HttpResponse(HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED,
                HttpStatus.UNAUTHORIZED.getReasonPhrase().toUpperCase(),
                SecurityConstant.ACCESS_DENIED_MESSAGE);
        // set the content type of the response to JSON
        response.setContentType(APPLICATION_JSON_VALUE);
        // set the status of the response to unauthorized
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
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
