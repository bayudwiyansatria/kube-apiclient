package com.bayudwiyansatria.spring.config;

import com.bayudwiyansatria.spring.util.ServerApiKeyAuthenticationFilter;
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
 * Security configuration class for the application. Configures HTTP security settings, including
 * CSRF protection, request authorization, HTTP basic authentication, and session management.
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${application.http.auth-token.key}")
    private String principalRequestHeader;

    @Value("${application.http.auth-token.value}")
    private String principalRequestValue;

    /**
     * Configures the security filter chain. Disables CSRF protection, allows public access to all
     * endpoints, enables HTTP basic authentication, and sets session management to stateless.
     *
     * @param http the HttpSecurity to modify
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs while configuring the security filter chain
     * @since 0.0.1
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

            // Security filter chain
            .securityMatcher("/api/**")

            // Add API Key filter before other filters
            .addFilterBefore(
                new ServerApiKeyAuthenticationFilter(
                    principalRequestHeader,
                    principalRequestValue
                ),
                UsernamePasswordAuthenticationFilter.class
            )

            // CSRF protection is disabled
            .csrf(AbstractHttpConfigurer::disable)

            // Set session management to stateless
            .sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
