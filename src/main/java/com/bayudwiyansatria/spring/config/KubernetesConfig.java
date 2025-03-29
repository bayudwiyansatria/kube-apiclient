package com.bayudwiyansatria.spring.config;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.util.Config;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KubernetesConfig {

    public ApiClient getApiClient() throws Exception {
        ApiClient client = Config.fromConfig("/home/bayudwiyansatria/.kube/config");
        io.kubernetes.client.openapi.Configuration.setDefaultApiClient(client);
        return client;
    }

    public CoreV1Api CoreClient() throws Exception {
        return new CoreV1Api(this.getApiClient());
    }
}
