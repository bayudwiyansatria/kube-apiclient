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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/**
 * A filter that performs API key-based authentication by validating requests against a configured
 * API key. This filter extends Spring Security's GenericFilterBean and checks for the presence and
 * validity of an API key in the request header.
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
public class ServerApiKeyAuthenticationFilter extends GenericFilterBean {

    /**
     * The name of the request header containing the API key
     */
    private final String principalRequestHeader;

    /**
     * The expected value of the API key
     */
    private final String principalRequestValue;

    /**
     * Constructs a new ServerApiKeyAuthenticationFilter with the specified header name and expected
     * value.
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
     * Extracts the API Key header from the request, validates it against the configured secret, and
     * sets the resulting Authentication object into the SecurityContext. If validation fails, an
     * unauthorized status code (401) is sent in the response.
     *
     * @param request     the incoming ServletRequest
     * @param response    the outgoing ServletResponse
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
