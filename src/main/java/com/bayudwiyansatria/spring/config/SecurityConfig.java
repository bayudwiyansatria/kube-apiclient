package com.bayudwiyansatria.spring.config;

import com.bayudwiyansatria.spring.util.ServerApiKeyAuthenticationFilter;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfig
 * <p>
 * This class provides the security configuration for the application, which includes the
 * configuration of HTTP security settings such as CSRF protection, authentication, session
 * management, and endpoint authorization. It defines the security rules for accessing the API
 * endpoints and integrates API key authentication using custom filters.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *   <li>Disables CSRF protection for stateless APIs</li>
 *   <li>Configures stateless session management</li>
 *   <li>Integrates API Key authentication using a custom filter</li>
 *   <li>Allows unauthenticated access to Swagger UI and API documentation</li>
 *   <li>Secures all other API endpoints requiring authentication</li>
 * </ul>
 *
 * <p>
 * This class ensures that only requests with a valid API key in the request header can access the
 * application’s endpoints, except for the Swagger UI and API documentation, which are open to the public.
 * </p>
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@Getter
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * The header key used for API authentication. This value is injected from application
     * properties. The header will contain the API key required for authentication.
     */
    @Value("${spring.application.api.auth-token.key}")
    private String principalRequestHeader;

    /**
     * The expected value of the API key for authentication. This value is injected from application
     * properties. It is used to verify the authenticity of the incoming request.
     */
    @Value("${spring.application.api.auth-token.value}")
    private String principalRequestValue;

    /**
     * Configures the security filter chain.
     * <p>
     * This method defines the following security settings:
     * </p>
     *
     * <ul>
     *   <li>Disables CSRF protection as the application is stateless and does not require cookies or sessions.</li>
     *   <li>Configures session management to be stateless, meaning no sessions will be created or used.</li>
     *   <li>Registers the custom {@link ServerApiKeyAuthenticationFilter} to intercept requests and validate the API key.</li>
     *   <li>Excludes Swagger UI and API documentation from authentication, allowing unauthenticated access.</li>
     *   <li>Requires authentication for all other API requests.</li>
     * </ul>
     *
     * @param http the HttpSecurity object to configure the security settings
     * @return the configured {@link SecurityFilterChain} used by Spring Security
     * @throws Exception if an error occurs while configuring the security filter chain
     * @since 0.0.1
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Security filter chain
            .securityMatcher("/api/**")

            // CSRF protection is disabled
            .csrf(AbstractHttpConfigurer::disable)

            // Set session management to stateless
            .sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Add API Key filter before other filters
            .addFilterBefore(
                new ServerApiKeyAuthenticationFilter(
                    principalRequestHeader,
                    principalRequestValue
                ),
                UsernamePasswordAuthenticationFilter.class
            )

            // Ignore authentication for the Swagger UI and API docs
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
            );

        return http.build();
    }
}
