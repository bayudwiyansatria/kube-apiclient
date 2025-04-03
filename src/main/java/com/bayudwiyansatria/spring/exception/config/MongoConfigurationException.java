package com.bayudwiyansatria.spring.exception.config;

/**
 * MongoConfigurationException
 *
 * <p>
 * This class represents an exception that is thrown when there is a configuration error with
 * MongoDB.
 * </p>
 *
 * <p>
 * It extends {@code RuntimeException} and provides constructors to create an exception with a
 * message and an optional cause. This exception is typically thrown when there is a problem with
 * MongoDB connection settings, such as invalid configuration parameters or connection issues.
 * </p>
 *
 * <h2>Use Case:</h2>
 * This exception can be thrown when the application encounters configuration issues related to
 * MongoDB, such as incorrect database connection settings or authentication errors.
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
public class MongoConfigurationException extends RuntimeException {

    /**
     * Constructs a new MongoConfigurationException with the specified detail message.
     * <p>
     * This constructor is used when a specific error message is needed, but the cause of the
     * exception does not need to be specified.
     * </p>
     *
     * @param message the detail message, which is saved for later retrieval by the
     *                {@link Throwable#getMessage()} method
     * @since 0.0.1
     */
    public MongoConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructs a new MongoConfigurationException with the specified detail message and cause.
     * <p>
     * This constructor is used when both an error message and the cause (another exception) are
     * provided. The cause of the exception can be retrieved later via the
     * {@link Throwable#getCause()} method.
     * </p>
     *
     * @param message the detail message, which is saved for later retrieval by the
     *                {@link Throwable#getMessage()} method
     * @param cause   the cause of the exception, which is saved for later retrieval by the
     *                {@link Throwable#getCause()} method (can be {@code null})
     * @since 0.0.1
     */
    public MongoConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
