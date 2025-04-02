package com.bayudwiyansatria.spring.service.impl;

import com.bayudwiyansatria.spring.service.KubernetesService;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class KubernetesServiceImpl implements KubernetesService {

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
