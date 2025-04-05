package com.bayudwiyansatria.spring.util;

import lombok.Getter;

/**
 * Represents a secret key-value pair in the application.
 * <p>
 * This enum is used to define the keys for secret key-value pairs in the application. Each constant
 * represents a specific key that can be used to store or retrieve secret values.
 * </p>
 *
 * <p>
 * This enum is used to define the keys for secret key-value pairs in the application. Each constant
 * represents a specific key that can be used to store or retrieve secret values.
 * </p>
 *
 * @author Bayu Dwiyan Satria
 * @version 1.0.0
 */
@Getter
public enum SecretEnum {

    /**
     * Represents a secret key-value pair.
     * <p>
     * This enum is used to define the keys for secret key-value pairs in the application. Each
     * constant represents a specific key that can be used to store or retrieve secret values.
     * </p>
     */
    SERVICE_ACCOUNT("service-account-token", "kubernetes.io/service-account-token"),
    DOCKER_CFG("dockercfg", "kubernetes.io/dockercfg"),
    DOCKER_CFG_JSON("dockerconfigjson", "kubernetes.io/dockerconfigjson"),
    BASIC_AUTH("basic-auth", "kubernetes.io/basic-auth"),
    SSH_AUTH("ssh-auth", "kubernetes.io/ssh-auth"),
    TLS("tls", "kubernetes.io/tls"),
    TOKEN("token", "kubernetes.io/token"),
    OPAQUE("opaque", "Opaque");


    /**
     * The value of the secret key.
     *
     * <p>
     * This field holds the string representation of the secret key. It is used to identify the
     * specific key associated with a secret value.
     * </p>
     *
     * <p>
     * This method retrieves the string representation of the secret key associated with this enum
     * constant.
     * </p>
     */
    private final String value;

    /**
     * The custom string associated with the enum.
     * <p>This is the string you want to return instead of the default name.</p>
     * <p>This method retrieves the custom string value associated with this enum constant.</p>
     */
    private final String kubernetesValue;

    /**
     * Constructs a new SecretEnum with the specified value.
     * <p>This constructor initializes the enum constant with the given value.</p>
     *
     * @param value the string representation of the secret key
     */
    SecretEnum(String value, String kubernetesValue) {
        this.value = value;
        this.kubernetesValue = kubernetesValue;
    }

}
