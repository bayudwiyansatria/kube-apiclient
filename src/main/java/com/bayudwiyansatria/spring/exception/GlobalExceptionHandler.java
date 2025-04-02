package com.bayudwiyansatria.spring.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * GlobalExceptionHandler
 * <p>
 * This class provides global exception handling for the application. It handles specific exceptions
 * and returns appropriate HTTP responses.
 *
 * <p>It is annotated with {@code @ControllerAdvice} to indicate that it provides centralized
 * exception handling across all {@code @RequestMapping} methods.</p>
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles {@code RuntimeException} and returns a 404 Not Found status.
     *
     * @param e the RuntimeException to handle
     * @return the error message
     * @since 0.0.1
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(RuntimeException e) {
        logger.error(e.getMessage(), e);
        return e.getMessage();
    }

    /**
     * Handles {@code MongoConfigurationException} and returns a 500 Internal Server Error status.
     *
     * @param e the MongoConfigurationException to handle
     * @return a ResponseEntity with the error message and HTTP status
     * @since 0.0.1
     */
    @ExceptionHandler(MongoConfigurationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleMongoConnectionException(MongoConfigurationException e) {
        return new ResponseEntity<>(
            "MongoDB Connection Failed: " + e.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    /**
     * Handles {@code KubernetesException} and returns a 500 Internal Server Error status.
     *
     * @param e the KubernetesException to handle
     * @return a ResponseEntity with the error message and HTTP status
     */
    @ExceptionHandler(KubernetesConfigurationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleKubernetesException(KubernetesConfigurationException e) {
        return new ResponseEntity<>(
            e.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
