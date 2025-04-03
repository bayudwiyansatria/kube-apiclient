/**
 * This package contains the model classes for handling requests related to Kubernetes secrets in
 * the Kube API Client application.
 *
 * <p>
 * The classes in this package define the structure of request objects that interact with the
 * Kubernetes API, specifically for managing Kubernetes secrets. These models encapsulate the data
 * for creating, updating, or deleting Kubernetes secrets, providing a seamless integration with the
 * Kubernetes API and the Spring Boot application.
 * </p>
 *
 * <h2>Key Components</h2>
 * <ul>
 *   <li>{@link com.bayudwiyansatria.spring.model.request.RequestSecretEntity}: Represents a request to manage a Kubernetes secret, including its namespace,
 *       name, type, and associated data (key-value pairs stored in the secret).</li>
 * </ul>
 *
 * <h2>Features</h2>
 * <ul>
 *   <li>Defines the structure for requests to interact with Kubernetes secrets.</li>
 *   <li>Models are used for creating and updating secrets within the Kubernetes cluster.</li>
 * </ul>
 *
 * <p>
 * For more information, refer to the project's
 * <a href="https://github.com/bayudwiyansatria/kube-apiclient">GitHub repository</a>.
 * </p>
 *
 * @author Bayu Dwiyan Satria
 * @version 1.0.0
 */
package com.bayudwiyansatria.spring.model.request;