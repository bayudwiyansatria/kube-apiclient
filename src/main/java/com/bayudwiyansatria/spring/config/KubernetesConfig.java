package com.bayudwiyansatria.spring.config;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.util.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Kubernetes API client integration.
 * <p>
 * This class provides methods to configure and retrieve Kubernetes API clients using the kubeconfig
 * file specified in the application properties.
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration
public class KubernetesConfig {

    /**
     * Path to the kubeconfig file, injected from the application properties.
     */
    @Value("${spring.cloud.kubernetes.client.kubeconfig-file}")
    private String kubeConfig;

    /**
     * Creates and configures an ApiClient instance using the kubeconfig file.
     * <p>
     * The ApiClient is the primary client used to interact with the Kubernetes API.
     *
     * @return a configured ApiClient instance
     * @throws RuntimeException if the kubeconfig file cannot be loaded
     */
    @Bean
    public ApiClient apiClient() {
        try {
            ApiClient client = Config.fromConfig(this.kubeConfig);
            io.kubernetes.client.openapi.Configuration.setDefaultApiClient(client);
            return client;
        } catch (Exception e) {
            throw new RuntimeException("Failed to configure Kubernetes ApiClient", e);
        }
    }

    /**
     * Creates a CoreV1Api client using the configured ApiClient.
     * <p>
     * The CoreV1Api is used to perform operations on Kubernetes core resources, such as secrets,
     * pods, and config maps.
     *
     * @return a CoreV1Api instance
     */
    @Bean
    public CoreV1Api coreV1Api() {
        return new CoreV1Api(this.apiClient());
    }
}