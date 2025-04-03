package com.bayudwiyansatria.spring.service;

import io.kubernetes.client.openapi.models.V1ObjectMeta;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * Service interface for interacting with Kubernetes resources.
 * <p>
 * This interface defines the methods required to manage and interact with Kubernetes resources,
 * specifically for creating metadata associated with Kubernetes objects.
 * </p>
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
public interface KubernetesService {

    /**
     * Creates metadata for a Kubernetes resource.
     * <p>
     * This method generates an instance of {@link V1ObjectMeta} with the provided namespace, name,
     * labels, and annotations.
     * </p>
     *
     * @param namespace   the namespace for the Kubernetes resource
     * @param name        the name of the Kubernetes resource
     * @param labels      a map of labels to associate with the resource
     * @param annotations a map of annotations to associate with the resource
     * @return a {@link V1ObjectMeta} object that contains the metadata for the Kubernetes resource
     * @since 0.0.1
     */
    V1ObjectMeta createMetaData(
        String namespace,
        String name,
        Map<String, String> labels,
        Map<String, String> annotations
    );
}
