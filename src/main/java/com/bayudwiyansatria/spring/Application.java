package com.bayudwiyansatria.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Spring Boot application.
 * <p>
 * This class is annotated with {@code @SpringBootApplication}, which enables auto-configuration,
 * component scanning, and configuration properties in the Spring Boot application.
 * </p>
 * <p>
 * The {@code main} method in this class is the entry point of the application, invoking
 * {@code SpringApplication.run} to bootstrap the application.
 * </p>
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@SpringBootApplication
public class Application {

    /**
     * Main method to launch the Spring Boot application.
     * <p>
     * This method serves as the entry point for the application and calls
     * {@code SpringApplication.run} to start the application context, enabling the Spring Boot
     * application to run.
     * </p>
     *
     * @param args command-line arguments passed to the application
     * @since 0.0.1
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
