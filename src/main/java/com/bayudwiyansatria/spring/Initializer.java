package com.bayudwiyansatria.spring;

import jakarta.servlet.ServletContext;
import org.springframework.web.WebApplicationInitializer;

/**
 * Web application initializer for configuring the Servlet context programmatically.
 * <p>
 * This class implements the {@link WebApplicationInitializer} interface, which is used to configure
 * the Servlet context programmatically. By implementing this interface, the application is able to
 * initialize necessary components before the Spring context is fully initialized.
 * </p>
 * <p>
 * The {@code onStartup} method is overridden to perform specific actions during the initialization
 * of the Servlet context, such as adding filters, listeners, or performing other context-specific
 * configurations.
 * </p>
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
public class Initializer implements WebApplicationInitializer {

    /**
     * Initializes the Servlet context and configures application-specific settings.
     * <p>
     * This method is automatically invoked during the startup of the web application. Implement
     * this method to perform any necessary Servlet context configuration, such as registering
     * filters, listeners, or setting up context parameters.
     * </p>
     *
     * @param servletContext the Servlet context to be configured
     * @since 0.0.1
     */
    @Override
    public void onStartup(ServletContext servletContext) {
    }
}