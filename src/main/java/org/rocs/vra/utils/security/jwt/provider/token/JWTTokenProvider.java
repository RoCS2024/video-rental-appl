package org.rocs.vra.utils.security.jwt.provider.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.rocs.vra.domain.user.principal.UserPrincipal;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.rocs.vra.utils.security.constant.SecurityConstant.*;
import static java.util.Arrays.stream;

/**
 *  A custom component that handles the entire lifecycle of a JSON Web Token (JWT)
 *  While Spring Security provides a built-in JwtAuthenticationProvider for validating JWT tokens in OAuth 2.0 flows,
 *  it does not include features for generating tokens.
 *  Therefore, most applications implement their own JwtTokenProvider component
 *  to create, validate, and parse JWT tokens for authentication and authorization.
 */
@Component
public class JWTTokenProvider {

    // add this to properties or yaml file
    @Value("${jwt.secret}")
    private String secret;

    public String generateJwtToken(UserPrincipal userPrincipal) {
        // get all the claims (authorities, permissions, etc)
        String[] claims = getClaimsFromUser(userPrincipal);

        // create JWT token; add issuer (organization) and audience
        return JWT.create().withIssuer(ROCS).withAudience(ROCS_ADMINISTRATION)
                // set issued date with the username of the user together with all its authorities
                .withIssuedAt(new Date()).withSubject(userPrincipal.getUsername()).withArrayClaim(AUTHORITIES, claims)
                // set expiration of the token
                .withExpiresAt(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                // add signature with the secret (salt)
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }

    /**
     * Retrieves the authorities of the user from token
     */
    public List<GrantedAuthority> getAuthorities(String token) {
        String[] claims = getClaimsFromToken(token);
        return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    /**
     * Creates a mew Authentication token from the user and its authorities.
     * Builds user details from a request and attached it to the token.
     */
    public Authentication getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken userPassAuthToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
        userPassAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return userPassAuthToken;
    }

    /**
     * Checks if the token is valid using JWTVerifier by checking if username is present
     * and token is not yet expired
     */
    public boolean isTokenValid(String username, String token) {
        JWTVerifier verifier = getJWTVerifier();
        return StringUtils.isNotEmpty(username) && !isTokenExpired(verifier, token);
    }

    /**
     * Retrieves the subject from the token after verifying the token using JWTVerifier.
     */
    public String getSubject(String token) {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getSubject();
    }

    /**
     * Retrieves the authorities of the user from UserPrincipal
     */
    private String[] getClaimsFromUser(UserPrincipal user) {
        List<String> authorities = new ArrayList<>();
        for(GrantedAuthority grantedAuthority: user.getAuthorities()) {
            authorities.add(grantedAuthority.getAuthority());
        }
        return authorities.toArray(new String[0]);
    }

    /**
     * Verifies token using JWTVerifier and retrieves the authorities of the user from UserPrincipal
     */
    private String[] getClaimsFromToken(String token) {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getClaim(AUTHORITIES).asArray(String.class);
    }

    /**
     * Creates and configures a JWT verifier using the HMAC512 algorithm.
     * This method initializes an Algorithm instance with the secret key stored in the secret field,
     * then builds a JWTVerifier that enforces the following validation rules:
     *  1. the token's signature must be valid using HMAC512.
     *  2. the token's issuer claim must exactly match the issuer.
     * If the verifier construction fails (e.g., due to an invalid algorithm or unsupported key format),
     * a JWTVerificationException is caught and rethrown with a generic error message.
     */
    private JWTVerifier getJWTVerifier() {
        JWTVerifier verifier;
        try {
            Algorithm algorithm = Algorithm.HMAC512(secret);
            verifier = JWT.require(algorithm).withIssuer(ROCS).build();
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED);
        }
        return verifier;
    }

    /**
     * Checks if the token is expired by comparing the current date/time if it is before the set expiration date/time.
     */
    private boolean isTokenExpired(JWTVerifier verifier, String token) {
        Date expiration = verifier.verify(token).getExpiresAt();
        return expiration.before(new Date());
    }

}
