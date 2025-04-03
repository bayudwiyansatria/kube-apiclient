package com.bayudwiyansatria.spring.service.impl;

import com.bayudwiyansatria.spring.service.KubernetesService;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * KubernetesServiceImpl
 * <p>
 * This class provides the implementation of the KubernetesService interface. It is responsible for
 * creating metadata objects for Kubernetes resources, such as Pods, Services, and other Kubernetes
 * objects.
 * </p>
 *
 * <p>The class is annotated with {@code @Service}, indicating that it is a service layer bean in
 * the Spring context.</p>
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
public class KubernetesServiceImpl implements KubernetesService {

    /**
     * Creates a V1ObjectMeta instance with the specified namespace, name, labels, and annotations.
     *
     * @param namespace   the namespace to associate with the metadata
     * @param name        the name of the Kubernetes resource
     * @param labels      a map of labels to assign to the resource
     * @param annotations a map of annotations to assign to the resource
     * @return a V1ObjectMeta object populated with the provided data
     * @since 0.0.1
     */
    @Override
    public V1ObjectMeta createMetaData(
        String namespace,
        String name,
        Map<String, String> labels,
        Map<String, String> annotations
    ) {
        return new V1ObjectMeta()
            .namespace(namespace)
            .name(name)
            .labels(labels)
            .annotations(annotations);
    }
}
