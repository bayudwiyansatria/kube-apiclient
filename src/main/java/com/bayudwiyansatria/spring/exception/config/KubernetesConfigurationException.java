package com.bayudwiyansatria.spring.exception.config;

/**
 * Custom exception class for handling Kubernetes configuration-related errors.
 *
 * <p>
 * This exception is thrown when there is a problem with the configuration of Kubernetes-related
 * components in the application. It extends {@link RuntimeException}, so it is an unchecked
 * exception and doesn't require explicit handling in method signatures.
 * </p>
 *
 * <h2>Use Case:</h2>
 * This exception can be used in scenarios where Kubernetes configuration fails, such as invalid
 * configuration values, missing configuration, or issues related to connecting to the Kubernetes
 * cluster.
 *
 * <p>
 * The exception provides constructors for setting a detailed error message and for wrapping other
 * exceptions that may cause this exception to be thrown.
 * </p>
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
public class KubernetesConfigurationException extends RuntimeException {

    /**
     * Constructs a new KubernetesConfigurationException with the specified detail message.
     * <p>
     * This constructor is used when a specific error message needs to be provided, but the cause of
     * the exception does not need to be specified.
     * </p>
     *
     * @param message the detail message, which is saved for later retrieval by the
     *                {@link Throwable#getMessage()} method
     * @since 0.0.1
     */
    public KubernetesConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructs a new KubernetesConfigurationException with the specified detail message and
     * cause.
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
    public KubernetesConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
