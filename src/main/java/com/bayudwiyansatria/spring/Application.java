package com.bayudwiyansatria.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application
 * <p>
 * This is the main class for the Spring Boot application. It is annotated with
 * {@code @SpringBootApplication} to enable auto-configuration and component scanning.
 *
 * <p>The {@code main} method is the entry point of the application.</p>
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@SpringBootApplication
public class Application {

    /**
     * Main Application Runner
     * <p>
     * This is the main method which uses {@code SpringApplication.run} to launch the application.
     *
     * @param args command-line arguments passed to the application
     * @since 0.0.1
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
