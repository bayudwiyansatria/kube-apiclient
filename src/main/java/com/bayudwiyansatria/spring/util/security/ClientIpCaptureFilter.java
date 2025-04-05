package com.bayudwiyansatria.spring.util.security;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

/**
 * A filter that captures and logs the client IP address for each incoming HTTP request. This filter
 * checks the {@link HttpServletRequest} for the "X-Forwarded-For" header (commonly set by proxies
 * or load balancers) to retrieve the client’s original IP address. If the header is not present or
 * is empty, the filter falls back to using the remote address of the request.
 *
 * <p>
 * The client IP address is logged for monitoring, auditing, or debugging purposes. This filter
 * ensures that the IP address is captured early in the request lifecycle before any other
 * processing occurs.
 * </p>
 *
 * <p>
 * This class can be used to track and log the client IP for security and analytics purposes. The
 * filter does not alter the flow of the request and simply logs the IP before passing the request
 * further down the filter chain.
 * </p>
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@Slf4j
public class ClientIpCaptureFilter implements Filter {

    /**
     * Initializes the filter. This method is called once when the filter is instantiated.
     *
     * <p>
     * The initialization method is a no-op in this implementation but can be extended to include
     * custom initialization logic if required in the future.
     * </p>
     *
     * @param filterConfig The filter configuration object, which can be used to retrieve filter
     *                     initialization parameters.
     * @throws ServletException If an error occurs during filter initialization.
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic, if necessary
    }

    /**
     * Captures the client IP address from the HTTP request and logs it.
     *
     * <p>
     * This method checks the "X-Forwarded-For" header, which is often set by proxies or load
     * balancers to forward the client’s original IP address. If the header is not present, the
     * filter falls back to using the remote address from the request.
     * </p>
     *
     * <p>
     * After extracting the client IP, it logs the address using SLF4J and proceeds with the request
     * processing. The request is passed to the next filter in the chain.
     * </p>
     *
     * @param request  The {@link ServletRequest} object that contains the request data.
     * @param response The {@link ServletResponse} object that contains the response data.
     * @param chain    The filter chain, used to pass the request and response along the filter
     *                 chain after processing.
     * @throws IOException      If an input or output error occurs while processing the request.
     * @throws ServletException If a servlet-specific error occurs.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Get the client IP address
        String clientIp = getClientIp(httpRequest);

        // Log the client IP address
        log.info("Client IP: {}", clientIp);

        // Proceed with the request
        chain.doFilter(request, response);
    }

    /**
     * Cleans up any resources used by the filter. This method is called once when the filter is
     * destroyed.
     *
     * <p>
     * This method is a no-op in this implementation, but it can be extended in the future for
     * cleanup logic if necessary.
     * </p>
     */
    @Override
    public void destroy() {
        // Cleanup logic, if necessary
    }

    /**
     * Retrieves the client IP address from the request, considering the possibility of proxies or
     * load balancers.
     *
     * <p>
     * The method first checks the "X-Forwarded-For" header to capture the original client IP. If
     * this header is absent, the method falls back to the remote address of the request. If the
     * header contains multiple IPs (in case of multiple proxies), the first IP in the list is
     * considered the client’s IP.
     * </p>
     *
     * @param request The {@link HttpServletRequest} object from which to retrieve the client IP
     *                address.
     * @return The client IP address, or the remote address if the "X-Forwarded-For" header is not
     * present.
     */
    private String getClientIp(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");

        if (clientIp == null || clientIp.isEmpty()) {
            clientIp = request.getRemoteAddr();
        } else {
            // In case of multiple proxies, the first IP is the client's IP
            clientIp = clientIp.split(",")[0];
        }

        return clientIp;
    }
}
