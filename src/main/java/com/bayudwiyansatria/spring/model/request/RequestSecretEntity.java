package com.bayudwiyansatria.spring.model.request;

import com.bayudwiyansatria.spring.model.entity.secrets.SecretEntity;
import java.util.List;
import lombok.Data;

/**
 * Represents a request object for creating or updating a Kubernetes secret.
 * <p>
 * This class is used to encapsulate the data necessary for creating or updating a Kubernetes
 * secret, including the namespace, name, type, and the key-value pairs that make up the secret.
 * </p>
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@Data
public class RequestSecretEntity {

    /**
     * The namespace in which the Kubernetes secret is located.
     * <p>
     * This field represents the Kubernetes namespace where the secret will be created or updated.
     * Namespaces in Kubernetes help organize and separate resources.
     * </p>
     */
    private String namespace;

    /**
     * The name of the Kubernetes secret.
     * <p>
     * This field holds the name of the Kubernetes secret. It uniquely identifies the secret within
     * a namespace.
     * </p>
     */
    private String name;

    /**
     * The type of the Kubernetes secret. Default is "Opaque".
     * <p>
     * This field represents the type of secret, which is typically "Opaque" (default). Opaque
     * secrets are used for storing arbitrary data such as passwords, OAuth tokens, etc.
     * </p>
     */
    private String type = "Opaque";

    /**
     * The key-value pairs representing the data of the Kubernetes secret.
     * <p>
     * This field contains a list of {@link SecretEntity} objects. Each object in the list
     * represents a key-value pair that makes up the Kubernetes secret.
     * </p>
     */
    private List<SecretEntity> data;
}
