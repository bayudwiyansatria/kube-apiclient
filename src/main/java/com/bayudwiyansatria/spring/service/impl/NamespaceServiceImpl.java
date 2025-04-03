package com.bayudwiyansatria.spring.service.impl;

import com.bayudwiyansatria.spring.config.KubernetesConfig;
import com.bayudwiyansatria.spring.exception.KubernetesConfigurationException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class NamespaceServiceImpl implements NamespaceService {

    private static final Logger logger = LoggerFactory.getLogger(NamespaceServiceImpl.class);

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

    protected void processData(V1Namespace data, List<String> entity) {
        entity.add(Objects.requireNonNull(data.getMetadata()).getName());
    }
}
