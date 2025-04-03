package com.bayudwiyansatria.spring.config;

import com.bayudwiyansatria.spring.exception.config.KubernetesConfigurationException;
import com.bayudwiyansatria.spring.util.logging.LogMessages;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.apis.VersionApi;
import io.kubernetes.client.util.Config;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Kubernetes API client integration.
 *
 * <p>
 * This class provides methods to configure and retrieve Kubernetes API clients using the kube
 * config file specified in the application properties. The class ensures that the Kubernetes client
 * is properly initialized using the provided config file and provides access to key Kubernetes API
 * functionalities, such as CoreV1Api and VersionApi.
 * </p>
 *
 * <h2>Key Components</h2>
 * <ul>
 *   <li>{@link ApiClient}: The primary client used to interact with the Kubernetes API.</li>
 *   <li>{@link CoreV1Api}: A client used for performing operations on core Kubernetes resources, such as pods, secrets, and config maps.</li>
 *   <li>{@link VersionApi}: A client for accessing Kubernetes version information and performing version-related operations.</li>
 * </ul>
 *
 * <h2>Exceptions</h2>
 * <p>
 * This class throws a {@link KubernetesConfigurationException} if the kube config file cannot be loaded
 * or if there are issues with the Kubernetes client initialization.
 * </p>
 *
 * @author Bayu Dwiyan Satria
 * @version 1.0.0
 */
@Configuration
public class KubernetesConfig {

    /**
     * Logger for logging messages and errors related to Kubernetes configuration.
     */
    private static final Logger logger = LoggerFactory.getLogger(KubernetesConfig.class);

    /**
     * Path to the kube config file, injected from the application properties. This file is used to
     * configure the Kubernetes API client with appropriate authentication and connection settings.
     */
    @Value("${spring.cloud.kubernetes.client.kube-config-file}")
    private String kubeConfig;

    /**
     * The API Client for Kubernetes, which is configured from the kube config file. It is used for
     * all interactions with the Kubernetes API.
     */
    @Getter
    private ApiClient apiClient;

    /**
     * Initializes the {@link ApiClient} instance using the provided kube config file.
     *
     * <p>
     * This method is annotated with {@link PostConstruct} to ensure it runs after the class has
     * been fully initialized. It loads the kube config file and sets up the Kubernetes client for
     * subsequent API interactions. If the config file cannot be loaded, an exception is thrown.
     * </p>
     *
     * @throws KubernetesConfigurationException if the kube config file cannot be loaded
     * @see Config#fromConfig(String) for loading the Kubernetes config from the specified path.
     */
    @PostConstruct
    public void init() {
        try {
            this.apiClient = Config.fromConfig(this.kubeConfig);
            io.kubernetes.client.openapi.Configuration.setDefaultApiClient(this.apiClient);
        } catch (IOException e) {
            logger.error(LogMessages.Configuration.Kubernetes.Config.INIT_FAILED, e);
            throw new KubernetesConfigurationException(
                LogMessages.Configuration.Kubernetes.Error.CONFIG_LOAD_FAILED,
                e
            );
        }
    }

    /**
     * Creates a {@link CoreV1Api} client using the configured {@link ApiClient}.
     *
     * <p>
     * The {@link CoreV1Api} is used to perform operations on Kubernetes core resources such as
     * pods, secrets, config maps, etc. This method provides access to the CoreV1Api client for use
     * in Kubernetes operations.
     * </p>
     *
     * @return a {@link CoreV1Api} instance configured with the Kubernetes {@link ApiClient}.
     * @see CoreV1Api
     */
    @Bean
    public CoreV1Api coreV1Api() {
        return new CoreV1Api(this.apiClient);
    }

    /**
     * Creates a {@link VersionApi} client using the configured {@link ApiClient}.
     *
     * <p>
     * The {@link VersionApi} is used to access Kubernetes version information and perform related
     * operations. This method provides access to the VersionApi client for use in checking
     * Kubernetes versions.
     * </p>
     *
     * @return a {@link VersionApi} instance configured with the Kubernetes {@link ApiClient}.
     * @see VersionApi
     */
    @Bean
    public VersionApi versionApi() {
        return new VersionApi(this.apiClient);
    }
}