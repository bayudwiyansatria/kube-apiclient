package com.bayudwiyansatria.spring.service.impl;

import com.bayudwiyansatria.spring.config.KubernetesConfig;
import com.bayudwiyansatria.spring.exception.KubernetesConfigurationException;
import com.bayudwiyansatria.spring.model.Response;
import com.bayudwiyansatria.spring.model.entity.SecretsEntity;
import com.bayudwiyansatria.spring.model.entity.secrets.SecretEntity;
import com.bayudwiyansatria.spring.service.KubernetesService;
import com.bayudwiyansatria.spring.service.SecretService;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
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
@Primary
public class SecretServiceImpl implements SecretService {

    private static final Logger logger = LoggerFactory.getLogger(SecretServiceImpl.class);

    private static final class LogMessages {

        static final String CONNECTION_INIT_SUCCESS = "Connection initialized successfully";
        static final String CONNECTION_INIT_FAILED = "Failed to initialize Connection";

        static final String RETRIEVING_SECRET = "Retrieving secret: {} in namespace: {}";
        static final String RETRIEVED_SECRET = "Retrieved secret successfully";
        static final String FAILED_RETRIEVE = "Failed to retrieve secret";

        static final String SECRET_ALREADY_EXISTS = "Secret already exists: {}";
        static final String SECRET_ALREADY_EXISTS_NON_DYNAMICALLY = "Secret already exists";
        static final String SECRET_NOT_FOUND = "Secret not found: {}";
        static final String SECRET_NOT_FOUND_NON_DYNAMICALLY = "Secret not found";

        static final String SECRET_CREATED = "Secret created: {}";
        static final String SECRET_CREATED_NON_DYNAMICALLY = "Secret created";
        static final String SECRET_CREATION_FAILED = "Secret creation failed";

        static final String SECRET_UPDATED = "Secret updated: {}";
        static final String SECRET_UPDATE_FAILED = "Secret update failed";

        static final String SECRET_DELETED = "Secret deleted: {}";
        static final String SECRET_DELETED_NON_DYNAMICALLY = "Secret deleted";
        static final String SECRET_DELETION_FAILED = "Secret deletion failed";

        static final String PROCESSING_SEQUENTIAL = "Retrieving secrets sequentially";
        static final String DELEGATING_PARALLEL = "Delegating to parallel implementation";

        static final String FAILED_DECODE = "Failed to decode secret value";
    }

    private static final class ResponseMessages {

        static final String SUCCESS = "Success";
        static final String FAILED = "Failed";
        static final String NO_CONTENT = "No content";
    }

    /**
     * The Kubernetes CoreV1Api client used to interact with the Kubernetes API.
     */
    protected final CoreV1Api coreClient;

    /**
     * The Kubernetes configuration used to initialize the API client.
     */
    private final KubernetesConfig kubernetesConfig;

    /**
     * The Kubernetes service used for additional operations.
     */
    private final KubernetesService kubernetesService;

    /**
     * Constructor for {@link SecretServiceImpl}.
     *
     * @param kubernetesConfig the {@link KubernetesConfig} to initialize the API client
     * @throws RuntimeException if initialization of {@link CoreV1Api} fails
     */
    public SecretServiceImpl(
        KubernetesConfig kubernetesConfig,
        KubernetesService kubernetesService
    ) {
        try {
            this.coreClient = kubernetesConfig.coreV1Api();
            this.kubernetesService = kubernetesService;
            this.kubernetesConfig = kubernetesConfig;
            logger.info(LogMessages.CONNECTION_INIT_SUCCESS);
        } catch (KubernetesConfigurationException e) {
            throw new KubernetesConfigurationException(LogMessages.CONNECTION_INIT_FAILED, e);
        }
    }

    /**
     * Retrieves secrets with optional parallelism support. Delegates to
     * {@link SecretServiceParallelismImpl} when parallelism is enabled.
     *
     * @param parallelism whether to use parallel processing
     * @return a {@link Response} containing a {@link List} of {@link SecretsEntity}
     */
    @Override
    public Response<?> list(boolean parallelism) {
        if (parallelism) {
            logger.info(LogMessages.DELEGATING_PARALLEL);
            return new SecretServiceParallelismImpl(
                kubernetesConfig,
                this.kubernetesService
            ).getSecrets();
        }
        logger.info(LogMessages.PROCESSING_SEQUENTIAL);
        return this.getSecrets();
    }

    /**
     * Retrieves a specific Kubernetes secret by its name and namespace.
     *
     * @param namespace the namespace of the secret
     * @param name      the name of the secret
     * @return a {@link Response} containing the {@link SecretsEntity}
     */
    @Override
    public Response<?> get(
        String namespace,
        String name
    ) {
        try {
            logger.info(LogMessages.RETRIEVING_SECRET, namespace, name);

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
                LogMessages.RETRIEVED_SECRET,
                HttpStatus.OK.value(),
                new SecretsEntity(
                    namespace,
                    name,
                    data
                )
            );
        } catch (ApiException e) {
            logger.error(LogMessages.SECRET_NOT_FOUND, name, e);
            return new Response<>(
                LogMessages.SECRET_NOT_FOUND_NON_DYNAMICALLY,
                HttpStatus.NOT_FOUND.value(),
                null
            );
        }
    }

    /**
     * Creates or updates a Kubernetes secret with the specified name, type, and data.
     *
     * @param namespace  the namespace of the secret
     * @param name       the name of the secret
     * @param type       the type of the secret
     * @param secretData the data to be stored in the secret
     * @return a {@link Response} indicating the result of the operation
     */
    @Override
    public Response<?> create(
        String namespace,
        String name,
        String type,
        List<SecretEntity> secretData
    ) {
        // Check if secret exists
        if (isSecretExist(namespace, name)) {
            logger.info(LogMessages.SECRET_ALREADY_EXISTS, name);
            return new Response<>(
                LogMessages.SECRET_ALREADY_EXISTS_NON_DYNAMICALLY,
                HttpStatus.CONFLICT.value(),
                null
            );
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
            logger.info(LogMessages.SECRET_CREATED);

            return new Response<>(
                LogMessages.SECRET_CREATED_NON_DYNAMICALLY,
                HttpStatus.CREATED.value(),
                new SecretsEntity(
                    namespace,
                    name,
                    secretData
                )
            );
        } catch (ApiException e) {
            logger.error(LogMessages.SECRET_CREATION_FAILED, e);
            return new Response<>(
                LogMessages.SECRET_CREATION_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                null
            );
        }
    }

    /**
     * Updates an existing Kubernetes secret.
     *
     * @return a {@link Response} indicating the result of the operation
     */
    @Override
    public Response<?> updateSecret() {
        // TODO
        return null;
    }

    /**
     * Deletes a Kubernetes secret by its name and namespace.
     *
     * @param namespace the namespace of the secret
     * @param name      the name of the secret
     * @return a {@link Response} indicating the result of the operation
     */
    @Override
    public Response<?> deleteSecret(String namespace, String name) {
        try {
            if (!isSecretExist(namespace, name)) {
                logger.info(LogMessages.SECRET_NOT_FOUND, name);
                return new Response<>(
                    LogMessages.SECRET_NOT_FOUND_NON_DYNAMICALLY,
                    HttpStatus.NOT_FOUND.value(),
                    null
                );
            }

            logger.info(LogMessages.SECRET_ALREADY_EXISTS, name);
            // Delete the secret
            this.coreClient.deleteNamespacedSecret(name, namespace).execute();

            return new Response<>(
                LogMessages.SECRET_DELETED_NON_DYNAMICALLY,
                HttpStatus.OK.value(),
                null
            );
        } catch (Exception e) {
            return new Response<>(
                LogMessages.SECRET_DELETION_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                null
            );
        }
    }

    /**
     * Retrieves a list of all Kubernetes secrets using sequential processing.
     *
     * @return a {@link Response} containing a {@link List} of {@link SecretsEntity}
     */
    protected Response<?> getSecrets() {
        try {
            logger.info("Retrieving secrets sequentially");
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
            logger.error(LogMessages.FAILED_DECODE, e);
            return new Response<>(
                LogMessages.FAILED_DECODE,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                null
            );
        }
    }

    /**
     * Processes an individual secret and adds it to the secrets entity list.
     *
     * @param secret        the {@link V1Secret} to process
     * @param secretsEntity the list to add the processed secret to
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
     * Decodes a Kubernetes secret value from a byte array to a UTF-8 string.
     *
     * @param value the byte array value of the secret
     * @return the decoded string value of the secret, or {@code null} if decoding fails
     */
    protected String getKubernetesSecret(byte[] value) {
        try {
            return new String(value, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            logger.error(LogMessages.FAILED_DECODE, e);
            return null;
        }
    }

    /**
     * Validates if the secret should be processed based on naming conventions.
     *
     * @param secret the {@link V1Secret} to validate
     * @return boolean indicating if the secret should be processed
     */
    protected boolean isValidSecret(V1Secret secret) {
        String name = Objects.requireNonNull(secret.getMetadata()).getName();

        if (name == null) {
            return false;
        }

        return !name.startsWith("sh.helm.release.v1") && !name.startsWith("default-token");
    }

    /**
     * Validates if a secret entry should be processed.
     *
     * @param secretName the name of the secret
     * @param entryKey   the key of the secret entry
     * @param entryValue the value of the secret entry
     * @return boolean indicating if the entry should be processed
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
     * Creates an appropriate response based on the secrets entity list.
     *
     * @param secretsEntity the list of processed secrets
     * @return {@link Response} containing the results
     */
    protected Response<List<SecretsEntity>> createResponse
    (List<SecretsEntity> secretsEntity) {
        if (!secretsEntity.isEmpty()) {
            return new Response<>(
                LogMessages.RETRIEVING_SECRET,
                HttpStatus.OK.value(),
                secretsEntity
            );
        }
        return new Response<>(
            LogMessages.RETRIEVING_SECRET,
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
            logger.info(LogMessages.SECRET_ALREADY_EXISTS, name);
            return true;
        }
        return false;
    }
}
