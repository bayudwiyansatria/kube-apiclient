package com.bayudwiyansatria.spring.util.logging;

/**
 * Utility class for centralized logging messages. This class contains all the log messages used
 * across the application, organized by different categories and contexts, such as configuration,
 * connection, secret management, processing, and error handling.
 * <p>
 * By centralizing all log messages in this class, the application maintains consistent and reusable
 * log messages, making it easier to manage and update logging strategies across the codebase.
 * </p>
 * <p>
 * This class is not meant to be instantiated, as it only serves as a container for logging
 * messages.
 * </p>
 * <p>
 * All log messages are organized into nested classes that reflect the different areas of the
 * application that require logging.
 * </p>
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
public final class LogMessages {

    // Private constructor to prevent instantiation
    private LogMessages() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Logging messages related to Kubernetes configuration.
     * <p>
     * This class contains log messages used for logging events and activities related to the
     * Kubernetes client configuration, including initialization, API communication, and error
     * handling.
     * </p>
     */
    public static final class Configuration {

        /**
         * Logging messages related to Kubernetes client configuration.
         * <p>
         * This inner class contains log messages for activities related to Kubernetes client
         * configuration, including initialization, success/failure of connections, and API call
         * handling.
         * </p>
         */
        public static final class Kubernetes {

            /**
             * Logging messages related to Kubernetes client configuration and setup.
             */
            public static final class Config {

                /**
                 * Logging messages related to the initialization of the Kubernetes client.
                 */
                public static final String INIT_START = "Initializing Kubernetes client with config: {}";

                /**
                 * Logging messages related to the successful initialization of the Kubernetes
                 * client.
                 */
                public static final String INIT_SUCCESS = "Successfully connected to Kubernetes cluster (version: {}) using context: {}";

                /**
                 * Logging messages related to the failure of Kubernetes client initialization.
                 */
                public static final String INIT_FAILED = "Failed to initialize Kubernetes client with config '{}': {}";

                /**
                 * Logging messages related to the current context being used in Kubernetes.
                 */
                public static final String CONTEXT_READ_FAILED = "Failed to read current context: {}";

                /**
                 * Logging messages related to the initialization of the Kubernetes client.
                 */
                public static final String CLIENT_NOT_INITIALIZED = "ApiClient not initialized, performing initialization";
            }

            /**
             * Logging messages related to Kubernetes API communication.
             */
            public static final class Api {

                /**
                 * Logging messages related to the initialization of the CoreV1Api client.
                 */
                public static final String CORE_INIT = "Initializing CoreV1Api client";

                /**
                 * Logging messages related to the readiness of the CoreV1Api client.
                 */
                public static final String CORE_READY = "CoreV1Api client ready";
            }

            /**
             * Logging messages related to errors encountered while interacting with Kubernetes.
             */
            public static final class Error {

                /**
                 * Logging messages related to configuration loading errors.
                 */
                public static final String CONFIG_LOAD_FAILED = "Failed to load Kubernetes configuration";

                /**
                 * Logging messages related to errors encountered while interacting with the
                 * Kubernetes
                 */
                public static final String API_ERROR = "Kubernetes API error: {}";

                /**
                 * Logging messages related to connection errors when interacting with Kubernetes.
                 */
                public static final String CONNECTION_ERROR = "Failed to connect to Kubernetes cluster";
            }

            /**
             * Logging messages providing informational details about the Kubernetes environment.
             */
            public static final class Info {

                /**
                 * Logging messages related to the Kubernetes cluster version.
                 */
                public static final String CLUSTER_VERSION = "Cluster version: {}";

                /**
                 * Logging messages related to the current context being used in Kubernetes.
                 */
                public static final String CURRENT_CONTEXT = "Current context: {}";

                /**
                 * Logging messages related to the namespace being used in Kubernetes.
                 */
                public static final String NAMESPACE = "Using namespace: {}";
            }

            /**
             * Logging messages used for debugging Kubernetes interactions.
             */
            public static final class Debug {

                /**
                 * Logging messages related to the kube config path.
                 */
                public static final String CONFIG_PATH = "Using kube config path: {}";

                /**
                 * Logging messages related to API calls made to the Kubernetes cluster.
                 */
                public static final String API_CALL = "Making API call: {} {}";

                /**
                 * Logging messages related to API response codes.
                 */
                public static final String RESPONSE_CODE = "Received response code: {}";
            }

            /**
             * Logging messages related to Kubernetes API metrics, such as latency and resource
             * counts.
             */
            public static final class Metrics {

                /**
                 * Logging messages related to API call latency.
                 */
                public static final String API_LATENCY = "API call latency: {}ms for {}";

                /**
                 * Logging messages related to resource counts in Kubernetes.
                 */
                public static final String RESOURCE_COUNT = "Found {} resources of type {}";
            }
        }
    }

    /**
     * Logging messages related to connection management.
     * <p>
     * This class contains log messages for the initialization and failure of the connection.
     * </p>
     */
    public static final class Connection {

        /**
         * Logging messages related to connection initialization.
         */
        public static final String INIT = "Connection Initializing: {}";

        /**
         * Logging messages related to successful connection initialization.
         */
        public static final String INIT_SUCCESS = "Connection initialized successfully";

        /**
         * Logging messages related to connection initialization failure.
         */
        public static final String INIT_FAILED = "Failed to initialize Connection";
    }

    /**
     * Logging messages related to secrets management.
     * <p>
     * This class contains log messages used to log actions regarding secrets, such as retrieving,
     * creating, updating, or deleting secrets, along with the success or failure of each action.
     * </p>
     */
    public static final class Service {

        /**
         * Logging messages related to secret retrieval and processing.
         * <p>
         * This class contains log messages for secret retrieval, including logging for both success
         * and failure scenarios.
         * </p>
         */
        public static final class Secret {

            /**
             * Logging messages related to secret retrieval operations.
             */
            public static final class Retrieve {

                /**
                 * Logging messages related to secret retrieval operations.
                 */
                public static final String PROCESS = "Retrieving secret: {} in namespace: {}";

                /**
                 * Logging messages related to successful secret retrieval operations.
                 */
                public static final String SUCCESS = "Retrieved secret successfully";

                /**
                 * Logging messages related to failed secret retrieval operations.
                 */
                public static final String FAILED = "Failed to retrieve secret";
            }

            /**
             * Logging messages related to the existence of secrets.
             * <p>
             * This class contains log messages for cases when secrets already exist in the
             * Kubernetes cluster, either dynamically or simply.
             * </p>
             */
            public static final class Exists {

                /**
                 * Logging messages related to secret existence operations.
                 */
                public static final String DYNAMIC = "Secret already exists: {}";

                /**
                 * Logging messages related to simple secret existence operations.
                 */
                public static final String SIMPLE = "Secret already exists";
            }

            /**
             * Logging messages related to secret not found scenarios.
             * <p>
             * This class logs cases where a requested secret is not found, either dynamically or
             * simply.
             * </p>
             */
            public static final class NotFound {

                /**
                 * Logging messages related to secret not found operations.
                 */
                public static final String DYNAMIC = "Secret not found: {}";

                /**
                 * Logging messages related to simple secret not found operations.
                 */
                public static final String SIMPLE = "Secret not found";
            }

            /**
             * Logging messages related to secret creation.
             * <p>
             * This class contains log messages for creating secrets, including success and
             * failure.
             * </p>
             */
            public static final class Create {

                /**
                 * Logging messages related to secret creation operations.
                 */
                public static final String DYNAMIC = "Secret created: {}";

                /**
                 * Logging messages related to simple secret creation operations.
                 */
                public static final String SIMPLE = "Secret created";

                /**
                 * Logging messages related to failed secret creation operations.
                 */
                public static final String FAILED = "Secret creation failed";
            }

            /**
             * Logging messages related to updating secrets.
             * <p>
             * This class contains log messages for updating secrets, including success and
             * failure.
             * </p>
             */
            public static final class Update {

                /**
                 * Logging messages related to secret update operations.
                 */
                public static final String DYNAMIC = "Secret updated: {}";

                /**
                 * Logging messages related to simple secret update operations.
                 */
                public static final String FAILED = "Secret update failed";
            }

            /**
             * Logging messages related to secret deletion.
             * <p>
             * This class contains log messages for deleting secrets, including success and
             * failure.
             * </p>
             */
            public static final class Delete {

                /**
                 * Logging messages related to secret deletion operations.
                 */
                public static final String DYNAMIC = "Secret deleted: {}";

                /**
                 * Logging messages related to simple secret deletion operations.
                 */
                public static final String SIMPLE = "Secret deleted";

                /**
                 * Logging messages related to failed secret deletion operations.
                 */
                public static final String FAILED = "Secret deletion failed";
            }
        }
    }

    /**
     * Logging messages related to processing activities (sequential vs parallel).
     * <p>
     * This class contains log messages that reflect the processing method used, whether secrets are
     * being retrieved sequentially or in parallel.
     * </p>
     */
    public static final class Processing {

        /**
         * Logging messages related to the processing of secrets.
         */
        public static final String SEQUENTIAL = "Retrieving secrets sequentially";

        /**
         * Logging messages related to parallel processing of secrets.
         */
        public static final String PARALLEL = "Delegating to parallel implementation";
    }

    /**
     * Logging messages related to errors in the application.
     * <p>
     * This class contains log messages for errors encountered during the application’s execution,
     * such as issues during the decoding of secret values.
     * </p>
     */
    public static final class Error {

        /**
         * Logging messages related to decoding errors.
         */
        public static final String DECODE_FAILED = "Failed to decode secret value";
    }
}
