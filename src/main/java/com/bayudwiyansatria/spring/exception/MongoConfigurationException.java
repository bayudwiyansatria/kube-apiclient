package com.bayudwiyansatria.spring.exception;

/**
 * MongoConfigurationException
 * <p>
 * This class represents an exception that is thrown when there is a configuration error with
 * MongoDB.
 *
 * <p>It extends {@code RuntimeException} and provides constructors to create an exception with a
 * message and an optional cause.</p>
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
public class MongoConfigurationException extends RuntimeException {

    /**
     * Constructs a new MongoConfigurationException with the specified detail message.
     *
     * @param message the detail message
     * @since 0.0.1
     */
    public MongoConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructs a new MongoConfigurationException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     * @since 0.0.1
     */
    public MongoConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
