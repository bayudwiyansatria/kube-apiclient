package com.bayudwiyansatria.spring.exception;

import com.bayudwiyansatria.spring.exception.config.KubernetesConfigurationException;
import com.bayudwiyansatria.spring.exception.config.MongoConfigurationException;
import com.bayudwiyansatria.spring.model.Response;
import com.bayudwiyansatria.spring.util.KubernetesErrorParser;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * GlobalExceptionHandler
 * <p>
 * This class provides global exception handling for the application. It centralizes the handling of
 * specific exceptions that occur during the execution of the application and ensures that
 * appropriate HTTP responses are returned. This includes mapping exceptions to specific HTTP status
 * codes and logging the error messages for troubleshooting.
 * </p>
 * <p>
 * It is annotated with {@code @ControllerAdvice}, which indicates that it provides centralized
 * exception handling across all {@code @RequestMapping} methods. The methods in this class handle
 * specific exceptions such as {@code RuntimeException}, {@code MongoConfigurationException}, and
 * {@code KubernetesConfigurationException}.
 * </p>
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@code RuntimeException} and returns a 404 Not Found status.
     * <p>
     * This method will be invoked whenever a {@code RuntimeException} is thrown by any of the
     * application's request handling methods. It logs the error message and then returns a 404 Not
     * Found status with the exception message.
     * </p>
     *
     * @param e the RuntimeException to handle
     * @return the error message
     * @since 0.0.1
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(RuntimeException e) {
        log.error(e.getMessage(), e);
        return e.getMessage();
    }

    /**
     * Handles {@code MongoConfigurationException} and returns a 500 Internal Server Error status.
     * <p>
     * This method will be invoked whenever a {@code MongoConfigurationException} is thrown,
     * indicating a MongoDB connection issue. It returns a {@link ResponseEntity} containing an
     * error message and the HTTP status of 500 Internal Server Error.
     * </p>
     *
     * @param e the MongoConfigurationException to handle
     * @return a ResponseEntity with the error message and HTTP status
     * @since 0.0.1
     */
    @ExceptionHandler(MongoConfigurationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleMongoConnectionException(MongoConfigurationException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(
            "MongoDB Connection Failed: " + e.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    /**
     * Handles {@code KubernetesConfigurationException} and returns a customized response with the
     * appropriate error details.
     * <p>
     * This method is invoked when a {@code KubernetesConfigurationException} occurs. It uses the
     * {@code KubernetesErrorParser} to parse the error message and returns a {@link ResponseEntity}
     * containing the error details and an appropriate HTTP status code.
     * </p>
     *
     * @param e the KubernetesConfigurationException to handle
     * @return a ResponseEntity containing the error message and HTTP status
     */
    @ExceptionHandler(KubernetesConfigurationException.class)
    public ResponseEntity<?> handleKubernetesException(
        KubernetesConfigurationException e
    ) {
        JsonNode errorBody = KubernetesErrorParser.parseResponseBody(e.getMessage());

        return new ResponseEntity<>(
            new Response<>(
                errorBody.path("message").asText("Unknown error"),
                errorBody.path("code").asInt(500),
                errorBody
            ),
            HttpStatus.valueOf(errorBody.path("code").asInt(500))
        );
    }
}
