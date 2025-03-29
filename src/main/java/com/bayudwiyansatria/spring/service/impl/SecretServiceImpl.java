package com.bayudwiyansatria.spring.service.impl;

import com.bayudwiyansatria.spring.config.KubernetesConfig;
import com.bayudwiyansatria.spring.model.entity.SecretsEntity;
import com.bayudwiyansatria.spring.model.entity.secrets.SecretEntity;
import com.bayudwiyansatria.spring.service.SecretService;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Secret;
import io.kubernetes.client.openapi.models.V1SecretList;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;

/**
 * Implementation of the SecretService interface for managing Kubernetes secrets.
 * <p>
 * This class interacts with the Kubernetes API to retrieve and process secrets.
 * 
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
public class SecretServiceImpl implements SecretService {

    private CoreV1Api coreClient;

    /**
     * Constructor for SecretServiceImpl.
     *
     * @param kubernetesConfig the Kubernetes configuration to initialize the API client
     */
    public SecretServiceImpl(KubernetesConfig kubernetesConfig) {
        try {
            this.coreClient = kubernetesConfig.coreV1Api();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves a list of all Kubernetes secrets.
     *
     * @return a list of {@link SecretsEntity} representing the secrets
     */
    @Override
    public List<SecretsEntity> getSecrets() {
        try {
            List<SecretsEntity> secretsEntity = new ArrayList<>();

            V1SecretList secrets = this.coreClient
                .listSecretForAllNamespaces()
                .execute();

            for (V1Secret secret : secrets.getItems()) {
                List<SecretEntity> secretData = new ArrayList<>();

                String name = Objects.requireNonNull(secret.getMetadata()).getName();
                Objects.requireNonNull(secret.getData()).forEach((key, value) -> {
                    secretData.add(
                        new SecretEntity(
                            key,
                            this.getKubernetesSecret(value)
                        )
                    );
                });

                secretsEntity.add(
                    new SecretsEntity(
                        name,
                        secretData
                    )
                );
            }

            return secretsEntity;
        } catch (Exception e) {
            System.err.println("Failed to decode value for key: " + e);
            return null;
        }
    }

    /**
     * Decodes a Kubernetes secret value from a byte array to a UTF-8 string.
     *
     * @param value the byte array value of the secret
     * @return the decoded string value of the secret
     */
    private String getKubernetesSecret(byte[] value) {
        try {
            return new String(value, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            System.err.println("Failed to decode value for key: " + key);
            return null;
        }
    }
}
