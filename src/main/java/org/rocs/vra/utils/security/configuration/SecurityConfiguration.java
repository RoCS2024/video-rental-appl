package org.rocs.vra.utils.security.configuration;

import org.rocs.vra.utils.security.constant.SecurityConstant;
import org.rocs.vra.utils.security.jwt.filter.authentication.access.denied.JWTAccessDeniedHandler;
import org.rocs.vra.utils.security.jwt.filter.authentication.forbidden.AuthenticationEntryPoint;
import org.rocs.vra.utils.security.jwt.filter.authorization.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Spring Security configuration class that sets up authentication, authorization,
 * and security filters for the application.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    /** JWT filter to validate tokens and set authentication. */
    private JWTAuthorizationFilter jwtAuthorizationFilter;

    /** Handler for 403 access denied responses (authenticated but no permission). */
    private JWTAccessDeniedHandler jwtAccessDeniedHandler;

    /** Handler for 401 authentication failures (missing/invalid token). */
    private AuthenticationEntryPoint authenticationEntryPoint;

    /** Service to load user details by username for authentication. */
    private UserDetailsService userDetailsService;

    /** BCrypt password encoder for hashing and verifying passwords. */
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /** Configuration providing the AuthenticationManager bean. */
    private final AuthenticationConfiguration authConfiguration;

    /**
     * Constructs the security configuration with required authentication and authorization components.
     *
     * @param jwtAuthorizationFilter    filter for validating JWT tokens
     * @param jwtAccessDeniedHandler    handler for access denied (403) responses
     * @param authenticationEntryPoint  handler for authentication failure (401) responses
     * @param userDetailsService        service to load user details by username
     * @param bCryptPasswordEncoder     password encoder for hashing and verification
     * @param authConfiguration         provides the authentication manager bean
     */
    @Autowired
    public SecurityConfiguration(JWTAuthorizationFilter jwtAuthorizationFilter,
                                 JWTAccessDeniedHandler jwtAccessDeniedHandler,
                                 AuthenticationEntryPoint authenticationEntryPoint,
                                 @Qualifier("userDetailsService")UserDetailsService userDetailsService,
                                 BCryptPasswordEncoder bCryptPasswordEncoder,
                                 AuthenticationConfiguration authConfiguration) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authConfiguration = authConfiguration;
    }

    /**
     * Configures the AuthenticationManagerBuilder with a custom
     * UserDetailsService and BCryptPasswordEncoder.
     */
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    /**
     * Exposes the AuthenticationManager bean for use throughout the application.
     */
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    /**
     * Creates and configures a CorsConfigurationSource bean that defines
     * Cross-Origin Resource Sharing (CORS) rules for the application.
     * @return
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        // Instantiates a new CorsConfiguration object.
        CorsConfiguration configuration = new CorsConfiguration();
        // Sets the allowed origin(s) for cross‑origin requests.
        // Only requests coming from http://localhost:8080 will be accepted.
        configuration.setAllowedOrigins(List.of("http://localhost:8080"));
        // Allows all request headers.
        // The wildcard "*" means any header sent by the client (e.g., Authorization, Content-Type) is permitted.
        configuration.setAllowedHeaders(List.of("*"));
        // Restricts allowed HTTP methods to only GET.
        // Cross‑origin requests using other methods like POST, PUT, DELETE, etc., will be rejected by the browser.
        configuration.setAllowedMethods(List.of("GET"));
        // Creates a UrlBasedCorsConfigurationSource.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Registers the previously defined CORS configuration for all URL paths (/**).
        // That means the same CORS rules (origin http://localhost:8080, headers *, methods GET)
        // apply to every endpoint in the application.
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // Disables CSRF (Cross‑Site Request Forgery) protection.
        // AbstractHttpConfigurer::disable is a lambda that tells Spring Security to turn off the default CSRF filter.
        // This is common for stateless REST APIs that use JWT (there is no session to protect, and the token itself provides security).
        http.csrf(AbstractHttpConfigurer::disable)
            // Enables CORS (Cross‑Origin Resource Sharing) support.
            // cors.configurationSource(...) provides a custom CorsConfigurationSource bean (defined elsewhere,
            // often via corsConfigurationSource() method) that specifies which origins, headers, and methods are allowed.
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            // Configures session management.
            // sessionCreationPolicy(SessionCreationPolicy.STATELESS) tells Spring Security never to create an HTTP session and
            // never to use it for storing security context. This is essential for JWT‑based APIs –
            // each request is independent and the token carries all necessary authentication data.
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // Defines authorization rules for HTTP requests.
            // requestMatchers(SecurityConstant.PUBLIC_URLS) – selects the URL patterns listed in SecurityConstant.PUBLIC_URLS
            //      (e.g., {"/user/login", "/user/register"}).
            // permitAll() – allows access to those URLs without any authentication.
            // anyRequest() – specifies the rule for all other requests (i.e., any URL not matched above).
            // authenticated() – requires that the user must be authenticated (have a valid JWT).
            .authorizeHttpRequests(auth -> auth.requestMatchers(SecurityConstant.PUBLIC_URLS).permitAll().anyRequest().authenticated())
            // Configures custom exception handling for security failures.
            .exceptionHandling((e) -> {
                e.authenticationEntryPoint(authenticationEntryPoint);
                e.accessDeniedHandler(jwtAccessDeniedHandler);})
            // Adds a custom JWT authorization filter before the standard BasicAuthenticationFilter.
            .addFilterBefore(jwtAuthorizationFilter, BasicAuthenticationFilter.class);
        // Builds the SecurityFilterChain object (an immutable chain of security filters) and returns it.
        // This chain will be inserted into Spring’s main FilterChainProxy and will be applied to every incoming request.
        return http.build();
    }

}
