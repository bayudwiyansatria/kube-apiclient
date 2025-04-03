package com.bayudwiyansatria.spring.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/**
 * A filter that performs API key-based authentication by validating requests against a configured
 * API key. This filter extends Spring Security's {@link GenericFilterBean} and checks for the
 * presence and validity of an API key in the request header.
 *
 * <p>
 * The filter extracts the API key from the request header, compares it to a configured expected
 * value, and if valid, sets an {@link Authentication} object in the {@link SecurityContextHolder}.
 * If validation fails, the filter returns an HTTP 401 Unauthorized response.
 * </p>
 *
 * <p>
 * This class is used to secure endpoints by requiring API key authentication.
 * </p>
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
public class ServerApiKeyAuthenticationFilter extends GenericFilterBean {

    /**
     * The name of the request header containing the API key.
     */
    private final String principalRequestHeader;

    /**
     * The expected value of the API key.
     */
    private final String principalRequestValue;

    /**
     * Constructs a new {@link ServerApiKeyAuthenticationFilter} with the specified header name and
     * expected value.
     *
     * @param principalRequestHeader the name of the request header containing the API key
     * @param principalRequestValue  the expected value of the API key that will be used for
     *                               validation
     */
    public ServerApiKeyAuthenticationFilter(
        String principalRequestHeader,
        String principalRequestValue
    ) {
        this.principalRequestHeader = principalRequestHeader;
        this.principalRequestValue = principalRequestValue;
    }

    /**
     * Extracts the API key from the request header, validates it against the configured secret, and
     * sets the resulting {@link Authentication} object into the {@link SecurityContext}. If the API
     * key is invalid, a 401 Unauthorized status code is returned in the response body.
     *
     * <p>
     * If the API key is valid, the request proceeds through the filter chain and is authorized. If
     * the API key is invalid, an error message is returned in the response.
     * </p>
     *
     * @param request     the incoming {@link ServletRequest}
     * @param response    the outgoing {@link ServletResponse}
     * @param filterChain the next filter in the chain
     * @throws IOException      if an I/O error occurs during processing
     * @throws ServletException if an error occurs that interferes with the filter chain
     */
    @Override
    public void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain filterChain
    ) throws IOException, ServletException {
        try {
            // Extract the API Key from the request header
            String apiKey = ((HttpServletRequest) request).getHeader(this.principalRequestHeader);

            // Validate the API Key against the configured secret
            if (apiKey == null || !apiKey.equals(this.principalRequestValue)) {
                throw new BadCredentialsException("Invalid API Key");
            }

            // Create an Authentication object with the API Key
            Authentication authentication = new ApiKeyAuthentication(
                apiKey,
                AuthorityUtils.NO_AUTHORITIES
            );

            // Set the Authentication object in the SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception exp) {
            // Handle authentication failure
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);

            // Set the response body with the error message
            PrintWriter writer = httpResponse.getWriter();

            writer.print(exp.getMessage());
            writer.flush();
            writer.close();

            return;
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
