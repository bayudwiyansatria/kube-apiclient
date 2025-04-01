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

    /**
     * The Kubernetes CoreV1Api client used to interact with the Kubernetes API.
     */
    protected final CoreV1Api coreClient;

    /**
     * The Kubernetes configuration used to initialize the API client.
     */
    private final KubernetesConfig kubernetesConfig;

    /**
     * Constructor for {@link SecretServiceImpl}.
     *
     * @param kubernetesConfig the {@link KubernetesConfig} to initialize the API client
     * @throws RuntimeException if initialization of {@link CoreV1Api} fails
     */
    public SecretServiceImpl(
        KubernetesConfig kubernetesConfig
    ) {
        try {
            this.kubernetesConfig = kubernetesConfig;
            this.coreClient = kubernetesConfig.coreV1Api();
        } catch (Exception e) {
            logger.error("Failed to initialize CoreV1Api", e);
            throw new RuntimeException("Failed to initialize Kubernetes client", e);
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
    public Response<List<SecretsEntity>> getSecrets(boolean parallelism) {
        if (parallelism) {
            logger.info("Delegating to parallel implementation");
            return new SecretServiceParallelismImpl(kubernetesConfig).getSecrets();
        }
        logger.info("Using sequential processing");
        return this.getSecrets();
    }

    /**
     * Retrieves a list of all Kubernetes secrets using sequential processing.
     *
     * @return a {@link Response} containing a {@link List} of {@link SecretsEntity}
     */
    protected Response<List<SecretsEntity>> getSecrets() {
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
            logger.error("Failed to retrieve secrets", e);
            return new Response<>(
                "Failed",
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

            secretsEntity.add(new SecretsEntity(name, secretData));
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
            logger.error("Failed to decode secret value", e);
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
    protected Response<List<SecretsEntity>> createResponse(List<SecretsEntity> secretsEntity) {
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
    }
}
