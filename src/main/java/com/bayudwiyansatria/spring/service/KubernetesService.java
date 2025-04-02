package com.bayudwiyansatria.spring.service;

import io.kubernetes.client.openapi.models.V1ObjectMeta;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public interface KubernetesService {

    V1ObjectMeta createMetaData(
        String namespace,
        String name,
        Map<String, String> labels,
        Map<String, String> annotations
    );
}
