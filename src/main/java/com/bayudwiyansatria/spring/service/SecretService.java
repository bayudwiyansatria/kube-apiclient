package com.bayudwiyansatria.spring.service;

import com.bayudwiyansatria.spring.model.Response;
import com.bayudwiyansatria.spring.model.entity.SecretsEntity;
import com.bayudwiyansatria.spring.model.entity.secrets.SecretEntity;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Service interface for managing Kubernetes secrets.
 * <p>
 * This interface defines the contract for operations related to Kubernetes secrets. Implementations
 * of this interface should provide the logic for retrieving, managing, creating, updating, and
 * deleting secrets in a Kubernetes cluster.
 * </p>
 *
 * <p>
 * It provides methods to perform actions such as listing secrets, retrieving secrets by namespace
 * and name, creating new secrets, updating existing secrets, and deleting secrets.
 * </p>
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
public interface SecretService {

    /**
     * Retrieves a list of all Kubernetes secrets.
     * <p>
     * This method returns all secrets, with an option to enable parallel processing for faster
     * retrieval, depending on the provided {@code parallelism} flag.
     * </p>
     *
     * @param parallelism a boolean indicating whether to use parallelism for the operation
     * @return a {@link Response} object containing a list of {@link SecretsEntity} representing the
     * secrets
     * @since 0.0.1
     */
    Response<?> list(boolean parallelism);

    /**
     * Retrieves a specific Kubernetes secret by its namespace and name.
     *
     * @param namespace the namespace of the secret
     * @param name      the name of the secret
     * @return a {@link Response} object containing a {@link SecretsEntity} representing the secret
     * @since 0.0.1
     */
    Response<?> get(
        String namespace,
        String name
    );

    /**
     * Creates a new Kubernetes secret in a specified namespace.
     * <p>
     * This method allows for the creation of a new secret by providing the secret's namespace,
     * name, type, and secret data.
     * </p>
     *
     * @param namespace the namespace of the secret
     * @param name      the name of the secret
     * @param type      the type of the secret
     * @param data      the data of the secret, represented as a list of {@link SecretEntity}
     * @return a {@link Response} object indicating the success or failure of the creation operation
     * @since 0.0.1
     */
    Response<?> create(
        String namespace,
        String name,
        String type,
        List<SecretEntity> data
    );

    /**
     * Updates an existing Kubernetes secret.
     * <p>
     * This method allows for updating an existing secret. The specific update logic will be
     * determined by the implementation.
     * </p>
     *
     * @return a {@link Response} object containing a list of updated {@link SecretsEntity}
     * @since 0.0.1
     */
    Response<?> updateSecret();

    /**
     * Deletes a specific Kubernetes secret by its namespace and name.
     * <p>
     * This method deletes the secret in the specified namespace with the given name.
     * </p>
     *
     * @param namespace the namespace of the secret to delete
     * @param name      the name of the secret to delete
     * @return a {@link Response} object indicating the success or failure of the deletion operation
     * @since 0.0.1
     */
    Response<?> delete(String namespace, String name);
}
