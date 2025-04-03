package com.bayudwiyansatria.spring.service.impl;

import com.bayudwiyansatria.spring.config.KubernetesConfig;
import com.bayudwiyansatria.spring.exception.config.KubernetesConfigurationException;
import com.bayudwiyansatria.spring.model.Response;
import com.bayudwiyansatria.spring.service.KubernetesService;
import com.bayudwiyansatria.spring.service.NamespaceService;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Namespace;
import io.kubernetes.client.openapi.models.V1NamespaceList;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * NamespaceServiceImpl
 * <p>
 * This class provides the implementation of the NamespaceService interface. It is responsible for
 * interacting with the Kubernetes API to manage namespaces within the Kubernetes cluster.
 * </p>
 *
 * <p>The class is annotated with {@code @Service}, indicating that it is a service component in
 * the Spring application context.</p>
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@Slf4j
@Service
public class NamespaceServiceImpl implements NamespaceService {

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
     * Constructs a new NamespaceServiceImpl with the specified Kubernetes configuration, CoreV1Api
     * client, and KubernetesService.
     *
     * @param coreClient        the CoreV1Api client for interacting with the Kubernetes API
     * @param kubernetesConfig  the Kubernetes configuration for setting up the API client
     * @param kubernetesService the KubernetesService used for additional operations
     * @throws KubernetesConfigurationException if there is an error in configuring the Kubernetes
     *                                          API client
     */
    public NamespaceServiceImpl(
        CoreV1Api coreClient,
        KubernetesConfig kubernetesConfig,
        KubernetesService kubernetesService
    ) {
        try {
            this.coreClient = kubernetesConfig.coreV1Api();
            this.kubernetesService = kubernetesService;
            this.kubernetesConfig = kubernetesConfig;
        } catch (KubernetesConfigurationException e) {
            throw new KubernetesConfigurationException("Error", e);
        }
    }


    /**
     * Retrieves a list of all Kubernetes namespaces in the cluster.
     *
     * @return a Response object containing the list of namespace names
     * @since 0.0.1
     */
    @Override
    public Response<?> list() {
        try {
            List<String> namespaceEntity = new ArrayList<>();

            V1NamespaceList namespaces = this.coreClient
                .listNamespace()
                .execute();

            namespaces
                .getItems()
                .forEach((data) -> processData(data, namespaceEntity));

            return new Response<>(
                "Success",
                HttpStatus.OK.value(),
                namespaceEntity
            );
        } catch (ApiException e) {
            throw new KubernetesConfigurationException("Error", e);
        }
    }

    /**
     * Processes the Kubernetes namespace data and adds the namespace name to the provided list.
     *
     * @param data   the V1Namespace object containing the namespace data
     * @param entity the list to which the namespace name will be added
     * @since 0.0.1
     */
    protected void processData(V1Namespace data, List<String> entity) {
        entity.add(Objects.requireNonNull(data.getMetadata()).getName());
    }
}
