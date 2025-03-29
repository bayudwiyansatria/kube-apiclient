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
        return e.getMessage();
    }

    /**
     * Handles {@code MongoConfigurationException} and returns a 500 Internal Server Error status.
     *
     * @param ex the MongoConfigurationException to handle
     * @return a ResponseEntity with the error message and HTTP status
     * @since 0.0.1
     */
    @ExceptionHandler(MongoConfigurationException.class)
    public ResponseEntity<String> handleMongoConnectionException(MongoConfigurationException ex) {
        // Return a custom error response with an appropriate message
        return new ResponseEntity<>("MongoDB Connection Failed: " + ex.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
