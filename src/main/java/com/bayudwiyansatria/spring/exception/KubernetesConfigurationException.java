package com.bayudwiyansatria.spring.exception;

public class KubernetesConfigurationException extends RuntimeException {

    /**
     * Constructs a new KubernetesException with the specified detail message.
     *
     * @param message the detail message
     */
    public KubernetesConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructs a new KubernetesException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public KubernetesConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
