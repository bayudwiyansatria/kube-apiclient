package com.bayudwiyansatria.spring;

import jakarta.servlet.ServletContext;
import org.springframework.web.WebApplicationInitializer;

/**
 * Initializer
 * <p>
 * This class implements {@code WebApplicationInitializer} to configure the Servlet context
 * programmatically.
 *
 * <p>The {@code onStartup} method is overridden to perform specific actions during the
 * initialization of the Servlet context.</p>
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
public class Initializer implements WebApplicationInitializer {

    /**
     * Perform specific actions during initialization.
     *
     * @param servletContext the Servlet context to be configured
     * @since 0.0.1
     */
    @Override
    public void onStartup(ServletContext servletContext) {
    }
}