package com.bayudwiyansatria.spring.service.impl;

import com.bayudwiyansatria.spring.config.KubernetesConfig;
import com.bayudwiyansatria.spring.model.Response;
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
import org.springframework.http.HttpStatus;
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
    public Response<List<SecretsEntity>> getSecrets() {
        try {
            List<SecretsEntity> secretsEntity = new ArrayList<>();

            // List all secrets across all namespaces
            V1SecretList secrets = this.coreClient
                .listSecretForAllNamespaces()
                .execute();

            // Loop through each secret
            for (V1Secret secret : secrets.getItems()) {
                List<SecretEntity> secretData = new ArrayList<>();

                // Get the name of the secret
                String name = Objects.requireNonNull(secret.getMetadata()).getName();

                // Perform case-insensitive filtering to exclude keys for helm
                if (name != null && !name.startsWith("sh.helm.release.v1")) {

                    Objects.requireNonNull(secret.getData()).forEach((key, value) -> {
                        // Processes the secret key and value
                        secretData.add(
                            new SecretEntity(
                                key,
                                this.getKubernetesSecret(value)
                            )
                        );
                    });

                    // Add processed secret data to the result list
                    secretsEntity.add(
                        new SecretsEntity(
                            name,
                            secretData
                        )
                    );
                }
            }

            if (!secretsEntity.isEmpty()) {
                return new Response<>(
                    "Success",
                    HttpStatus.OK.value(),
                    secretsEntity
                );
            }

            return new Response<>(
                "Success",
                HttpStatus.NO_CONTENT.value(),
                secretsEntity
            );
        } catch (Exception e) {
            return new Response<>(
                "Failed",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                null
            );
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
            System.err.println("Failed to decode value for value");
            return null;
        }
    }
}
