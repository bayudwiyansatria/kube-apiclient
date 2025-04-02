package com.bayudwiyansatria.spring.service.impl;

import com.bayudwiyansatria.spring.config.KubernetesConfig;
import com.bayudwiyansatria.spring.model.Response;
import com.bayudwiyansatria.spring.model.entity.SecretsEntity;
import com.bayudwiyansatria.spring.model.entity.secrets.SecretEntity;
import com.bayudwiyansatria.spring.service.KubernetesService;
import com.bayudwiyansatria.spring.service.SecretService;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Secret;
import io.kubernetes.client.openapi.models.V1SecretList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Parallel implementation of the {@link SecretService} interface for managing Kubernetes secrets.
 *
 * <p>
 * This class interacts with the Kubernetes API to retrieve and process secrets using
 * {@link CoreV1Api} client.
 * </p>
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
public class SecretServiceParallelismImpl extends SecretServiceImpl {

    Logger logger = LoggerFactory.getLogger(SecretServiceParallelismImpl.class);

    /**
     * Constructor for {@link SecretServiceParallelismImpl}.
     *
     * @param kubernetesConfig the {@link KubernetesConfig} to initialize the API client
     */
    public SecretServiceParallelismImpl(
        KubernetesConfig kubernetesConfig,
        KubernetesService kubernetesService
    ) {
        super(kubernetesConfig, kubernetesService);
    }

    /**
     * Retrieves a list of all Kubernetes secrets.
     *
     * @return a {@link Response} containing a {@link List} of {@link SecretsEntity} representing
     * the secrets
     */
    @Override
    public Response<List<SecretsEntity>> getSecrets() {
        try {
            logger.info("Retrieving secrets using parallel processing");
            List<SecretsEntity> secretsEntity = Collections.synchronizedList(new ArrayList<>());

            V1SecretList secrets = this.coreClient
                .listSecretForAllNamespaces()
                .execute();

            secrets.getItems()
                .parallelStream()
                .filter(this::isValidSecret)
                .forEach(secret -> processSecret(secret, secretsEntity));

            return this.createResponse(secretsEntity);
        } catch (Exception e) {
            logger.error("Failed to retrieve secrets in parallel", e);
            return new Response<>(
                "Failed",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                null
            );
        }
    }

    /**
     * Processes an individual secret with parallel processing of secret data.
     *
     * @param secret        the {@link V1Secret} to process
     * @param secretsEntity the thread-safe list to add the processed secret to
     */
    @Override
    protected void processSecret(V1Secret secret, List<SecretsEntity> secretsEntity) {
        String name = Objects.requireNonNull(secret.getMetadata()).getName();
        String namespace = Objects.requireNonNull(secret.getMetadata()).getNamespace();

        if (isValidSecret(secret)) {
            List<SecretEntity> secretData = Collections.synchronizedList(new ArrayList<>());

            Objects.requireNonNull(secret.getData())
                .entrySet()
                .parallelStream()
                .filter(entry -> {
                    String value = getKubernetesSecret(entry.getValue());
                    assert name != null;
                    return isValidSecretEntry(name, entry.getKey(), value);
                })
                .forEach(entry ->
                    secretData.add(
                        new SecretEntity(
                            entry.getKey(),
                            getKubernetesSecret(entry.getValue())
                        )
                    )
                );

            synchronized (secretsEntity) {
                secretsEntity.add(new SecretsEntity(namespace, name, secretData));
            }
        }
    }
}
