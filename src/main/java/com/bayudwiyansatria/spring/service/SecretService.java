package com.bayudwiyansatria.spring.service;

import com.bayudwiyansatria.spring.model.Response;
import com.bayudwiyansatria.spring.model.entity.SecretsEntity;
import com.bayudwiyansatria.spring.model.entity.secrets.SecretEntity;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Service interface for managing Kubernetes secrets.
 * <p>
 * This interface defines the contract for operations related to Kubernetes secrets.
 *
 * <p>
 * Implementations of this interface should provide the logic for retrieving and managing secrets.
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
public interface SecretService {

    /**
     * Retrieves a list of all Kubernetes secrets.
     *
     * @param parallelism a boolean indicating whether to use parallelism for the operation
     * @return a list of SecretsEntity representing the secrets
     */
    Response<?> list(boolean parallelism);

    /**
     * Retrieves a list of all Kubernetes secrets.
     *
     * @param namespace the namespace of the secret
     * @param name      the name of the secret
     * @return a list of SecretsEntity representing the secrets
     */
    Response<?> get(
        String namespace,
        String name
    );

    /**
     * Retrieves a list of all Kubernetes secrets.
     *
     * @param namespace the namespace of the secret
     * @param name      the name of the secret
     * @param type      the type of the secret
     * @param data      the data of the secret
     * @return a list of SecretsEntity representing the secrets
     */
    Response<?> create(
        String namespace,
        String name,
        String type,
        List<SecretEntity> data
    );

    /**
     * Updates the existing Kubernetes secrets.
     *
     * @return a list of SecretsEntity representing the updated secrets
     */
    Response<?> updateSecret();

    /**
     * Retrieves a specific Kubernetes secret by its name.
     *
     * @param namespace the namespace of the secret
     * @param name      the name of the secret
     * @return a SecretsEntity representing the secret
     */
    Response<?> deleteSecret(String namespace, String name);
}
