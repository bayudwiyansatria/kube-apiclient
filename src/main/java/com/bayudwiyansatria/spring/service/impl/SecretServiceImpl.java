package com.bayudwiyansatria.spring.service.impl;

import com.bayudwiyansatria.spring.config.KubernetesConfig;
import com.bayudwiyansatria.spring.exception.config.KubernetesConfigurationException;
import com.bayudwiyansatria.spring.model.Response;
import com.bayudwiyansatria.spring.model.entity.SecretsEntity;
import com.bayudwiyansatria.spring.model.entity.secrets.SecretEntity;
import com.bayudwiyansatria.spring.service.KubernetesService;
import com.bayudwiyansatria.spring.service.SecretService;
import com.bayudwiyansatria.spring.util.logging.LogMessages;
import com.bayudwiyansatria.spring.util.logging.LogMessages.Error;
import com.bayudwiyansatria.spring.util.logging.LogMessages.Processing;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Secret;
import io.kubernetes.client.openapi.models.V1SecretList;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Implementation of the SecretService interface for managing Kubernetes secrets. This class
 * provides functionality to create, read, update, and delete Kubernetes secrets across different
 * namespaces.
 *
 * <p>This implementation includes support for both sequential and parallel processing of secrets,
 * and handles various Kubernetes API responses and error conditions.</p>
 *
 * @author Bayu Dwiyan Satria
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Service
@Primary
public class SecretServiceImpl implements SecretService {

    /**
     * Core Kubernetes API client for secret operations.
     */
    protected final CoreV1Api coreClient;

    /**
     * Kubernetes configuration for API client initialization.
     */
    private final KubernetesConfig kubernetesConfig;

    /**
     * Kubernetes service for additional operations.
     */
    private final KubernetesService kubernetesService;

    /**
     * Constructs a new SecretServiceImpl with required dependencies.
     *
     * @param kubernetesConfig  the configuration for Kubernetes API client
     * @param kubernetesService the service for additional Kubernetes operations
     * @since 1.0.0
     */
    public SecretServiceImpl(
        KubernetesConfig kubernetesConfig,
        KubernetesService kubernetesService
    ) {
        this.coreClient = kubernetesConfig.coreV1Api();
        this.kubernetesService = kubernetesService;
        this.kubernetesConfig = kubernetesConfig;
    }

    /**
     * Lists all secrets across all namespaces with optional parallel processing.
     *
     * @param parallelism whether to process secrets in parallel
     * @return a {@link Response} containing:
     * <ul>
     *     <li>HTTP 200 (OK) with list of secrets if found</li>
     *     <li>HTTP 204 (NO_CONTENT) if no secrets exist</li>
     * </ul>
     * @throws KubernetesConfigurationException if there's an error retrieving secrets
     * @since 1.0.0
     */
    @Override
    public Response<?> list(boolean parallelism) {
        if (parallelism) {
            log.info(Processing.PARALLEL);
            return new SecretServiceParallelismImpl(
                kubernetesConfig,
                this.kubernetesService
            ).getSecrets();
        }
        log.info(Processing.SEQUENTIAL);
        return this.getSecrets();
    }

    /**
     * Retrieves a specific secret by name from a namespace.
     *
     * @param namespace the namespace containing the secret
     * @param name      the name of the secret to retrieve
     * @return a {@link Response} containing:
     * <ul>
     *     <li>HTTP 200 (OK) with the secret details if found</li>
     *     <li>HTTP 404 (NOT_FOUND) if the secret doesn't exist</li>
     * </ul>
     * @throws KubernetesConfigurationException if there's an error retrieving the secret
     * @since 1.0.0
     */
    @Override
    public Response<?> get(
        String namespace,
        String name
    ) {
        try {
            log.info(LogMessages.Service.Secret.Retrieve.PROCESS, name, namespace);

            // Retrieve the secret from the Kubernetes cluster
            List<SecretEntity> data = new ArrayList<>();
            V1Secret secret = this.coreClient.readNamespacedSecret(name, namespace).execute();

            // Process the secret data
            for (
                Map.Entry<String, byte[]> entry : Objects.requireNonNull(secret.getData())
                .entrySet()) {
                String key = entry.getKey();
                String value = getKubernetesSecret(entry.getValue());
                if (isValidSecretEntry(name, key, value)) {
                    data.add(new SecretEntity(key, value));
                }
            }

            return new Response<>(
                LogMessages.Service.Secret.Retrieve.SUCCESS,
                HttpStatus.OK.value(),
                new SecretsEntity(
                    namespace,
                    name,
                    data
                )
            );
        } catch (ApiException e) {
            throw new KubernetesConfigurationException(e.getMessage());
        }
    }

    /**
     * Creates a new Kubernetes secret.
     *
     * @param namespace  the namespace where the secret should be created
     * @param name       the name of the secret to create
     * @param type       the type of the secret (e.g., "Opaque", "kubernetes.io/tls")
     * @param secretData the list of key-value pairs to store in the secret
     * @return a {@link Response} containing:
     * <ul>
     *     <li>HTTP 201 (CREATED) with the created secret details on success</li>
     *     <li>HTTP 409 (CONFLICT) if the secret already exists</li>
     * </ul>
     * @throws KubernetesConfigurationException if there's an error creating the secret
     * @since 1.0.0
     */
    @Override
    public Response<?> create(
        String namespace,
        String name,
        String type,
        List<SecretEntity> secretData
    ) {
        try {
            // Check if secret exists using get method
            Response<?> existingSecret = this.get(namespace, name);

            // If get() returns 200, the secret exists
            if (existingSecret.getStatus() == HttpStatus.OK.value()) {
                return new Response<>(
                    LogMessages.Service.Secret.Exists.SIMPLE,
                    HttpStatus.CONFLICT.value(),
                    null
                );
            }
        } catch (KubernetesConfigurationException e) {
            if (!e.getMessage().contains("\"code\":404")) {
                throw e;
            }
        }

        // Create a Secret object
        V1Secret secret = new V1Secret();
        secret.setApiVersion("v1");
        secret.setMetadata(
            this.kubernetesService.createMetaData(
                namespace,
                name,
                new HashMap<>(),
                new HashMap<>()
            ));
        secret.setKind("Secret");

        // Set the type of the secret
        Map<String, String> data = new HashMap<>();
        for (SecretEntity secretEntity : secretData) {
            data.put(secretEntity.getKey(), secretEntity.getValue());
        }
        secret.setStringData(data);

        try {
            // Create the secret in the Kubernetes cluster
            this.coreClient.createNamespacedSecret(namespace, secret).execute();
            log.info(LogMessages.Service.Secret.Create.DYNAMIC, name);

            return new Response<>(
                LogMessages.Service.Secret.Create.SIMPLE,
                HttpStatus.CREATED.value(),
                new SecretsEntity(
                    namespace,
                    name,
                    secretData
                )
            );
        } catch (ApiException e) {
            log.error(LogMessages.Service.Secret.Create.FAILED, e);
            return new Response<>(
                LogMessages.Service.Secret.Create.FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                null
            );
        }
    }

    /**
     * Updates an existing Kubernetes secret.
     *
     * @return a {@link Response} indicating the result of the update operation
     * @throws KubernetesConfigurationException if there's an error updating the secret
     * @since 1.0.0
     */
    @Override
    public Response<?> updateSecret() {
        // TODO
        return null;
    }

    /**
     * Deletes a Kubernetes secret.
     *
     * @param namespace the namespace containing the secret
     * @param name      the name of the secret to delete
     * @return a {@link Response} containing:
     * <ul>
     *     <li>HTTP 200 (OK) if the secret was successfully deleted</li>
     *     <li>HTTP 404 (NOT_FOUND) if the secret doesn't exist</li>
     * </ul>
     * @throws KubernetesConfigurationException if there's an error deleting the secret
     * @since 1.0.0
     */
    @Override
    public Response<?> deleteSecret(String namespace, String name) {
        try {
            if (!isSecretExist(namespace, name)) {
                log.info(LogMessages.Service.Secret.NotFound.DYNAMIC, name);
                return new Response<>(
                    LogMessages.Service.Secret.NotFound.SIMPLE,
                    HttpStatus.NOT_FOUND.value(),
                    null
                );
            }

            log.info(LogMessages.Service.Secret.Exists.DYNAMIC, name);
            // Delete the secret
            this.coreClient.deleteNamespacedSecret(name, namespace).execute();

            return new Response<>(
                LogMessages.Service.Secret.Exists.SIMPLE,
                HttpStatus.OK.value(),
                null
            );
        } catch (Exception e) {
            return new Response<>(
                LogMessages.Service.Secret.Delete.FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                null
            );
        }
    }

    /**
     * Retrieves secrets using sequential processing.
     *
     * @return a {@link Response} containing the list of secrets
     * @throws KubernetesConfigurationException if there's an error retrieving secrets
     */
    protected Response<?> getSecrets() {
        try {
            log.info("Retrieving secrets sequentially");
            List<SecretsEntity> secretsEntity = new ArrayList<>();

            V1SecretList secrets = this.coreClient
                .listSecretForAllNamespaces()
                .execute();

            secrets.getItems()
                .stream()
                .filter(this::isValidSecret)
                .forEach(secret -> processSecret(secret, secretsEntity));

            return createResponse(secretsEntity);
        } catch (Exception e) {
            log.error(Error.DECODE_FAILED, e);
            return new Response<>(
                Error.DECODE_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                null
            );
        }
    }

    /**
     * Processes an individual secret and adds it to the secrets collection.
     *
     * @param secret        the Kubernetes secret to process
     * @param secretsEntity the collection to add the processed secret to
     */
    protected void processSecret(V1Secret secret, List<SecretsEntity> secretsEntity) {
        String name = Objects.requireNonNull(secret.getMetadata()).getName();
        String namespace = Objects.requireNonNull(secret.getMetadata()).getNamespace();

        if (isValidSecret(secret)) {
            List<SecretEntity> secretData = new ArrayList<>();

            Objects.requireNonNull(secret.getData()).forEach((key, byteValue) -> {
                String value = getKubernetesSecret(byteValue);
                assert name != null;
                if (isValidSecretEntry(name, key, value)) {
                    secretData.add(
                        new SecretEntity(
                            key,
                            value
                        )
                    );
                }
            });

            secretsEntity.add(new SecretsEntity(namespace, name, secretData));
        }
    }

    /**
     * Decodes a Kubernetes secret value from bytes to string.
     *
     * @param value the byte array containing the secret value
     * @return the decoded string value, or null if decoding fails
     */
    protected String getKubernetesSecret(byte[] value) {
        try {
            return new String(value, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            log.error(Error.DECODE_FAILED, e);
            return null;
        }
    }

    /**
     * Validates if a secret should be processed based on naming conventions.
     *
     * @param secret the Kubernetes secret to validate
     * @return true if the secret should be processed, false otherwise
     */
    protected boolean isValidSecret(V1Secret secret) {
        String name = Objects.requireNonNull(secret.getMetadata()).getName();

        if (name == null) {
            return false;
        }

        return !name.startsWith("sh.helm.release.v1") && !name.startsWith("default-token");
    }

    /**
     * Validates if a secret entry should be included in the response.
     *
     * @param secretName the name of the secret
     * @param entryKey   the key of the secret entry
     * @param entryValue the value of the secret entry
     * @return true if the entry should be included, false otherwise
     */
    protected boolean isValidSecretEntry(String secretName, String entryKey, String entryValue) {
        if (entryValue.isEmpty()) {
            return false;
        }

        if (entryKey.equals("namespace")) {
            return false;
        }

        if (secretName.contains("token")) {
            return !entryKey.equals("ca.crt");
        }
        return true;
    }

    /**
     * Creates a response based on the processed secrets.
     *
     * @param secretsEntity the list of processed secrets
     * @return a {@link Response} with appropriate status and data
     */
    protected Response<List<SecretsEntity>> createResponse
    (List<SecretsEntity> secretsEntity) {
        if (!secretsEntity.isEmpty()) {
            return new Response<>(
                LogMessages.Service.Secret.Retrieve.SUCCESS,
                HttpStatus.OK.value(),
                secretsEntity
            );
        }
        return new Response<>(
            LogMessages.Service.Secret.Retrieve.SUCCESS,
            HttpStatus.NO_CONTENT.value(),
            secretsEntity
        );
    }

    /**
     * Checks if a secret exists in the specified namespace.
     *
     * @param namespace the namespace of the secret
     * @param name      the name of the secret
     * @return boolean indicating if the secret exists
     */
    private boolean isSecretExist(String namespace, String name) {
        Response<?> existingSecret = this.get(namespace, name);
        if (existingSecret != null && existingSecret.getStatus() == HttpStatus.OK.value()) {
            log.info(LogMessages.Service.Secret.Exists.DYNAMIC, name);
            return true;
        }
        return false;
    }
}
