package com.bayudwiyansatria.spring.model.entity;

import com.bayudwiyansatria.spring.model.entity.secrets.SecretEntity;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a collection of Kubernetes secrets.
 * <p>
 * This class contains the details of a Kubernetes secret, including its namespace, name, and a list
 * of individual secret key-value pairs.
 * </p>
 *
 * <p>The {@code namespace} field identifies the Kubernetes namespace where the secret is located,
 * while the {@code name} field holds the name of the secret. The {@code secret} field contains a
 * list of {@link SecretEntity} objects that represent the key-value pairs of the secret.</p>
 *
 * <p>This entity is used to organize and manage secrets associated with Kubernetes
 * deployments.</p>
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecretsEntity {

    /**
     * The namespace in which the Kubernetes secret is located.
     * <p>
     * This field stores the namespace under which the Kubernetes secret is grouped. Kubernetes
     * secrets are organized in namespaces, so this field helps identify where the secret is located
     * in the cluster.
     * </p>
     */
    private String namespace;

    /**
     * The name of the Kubernetes secret.
     * <p>
     * This field holds the name of the Kubernetes secret, which uniquely identifies the secret
     * within a given namespace.
     * </p>
     */
    private String name;

    /**
     * The type of the Kubernetes secret.
     * <p>
     * This field indicates the type of the secret, which can be one of several predefined types
     * such as Opaque, DockerConfigJson, etc. The type helps Kubernetes understand how to handle the
     * secret data.
     * </p>
     */
    private String type;

    /**
     * A list of key-value pairs representing the secrets.
     * <p>
     * This field contains a list of {@link SecretEntity} objects. Each object in the list
     * represents a key-value pair that is part of the Kubernetes secret.
     * </p>
     */
    private List<SecretEntity> secret;
}
