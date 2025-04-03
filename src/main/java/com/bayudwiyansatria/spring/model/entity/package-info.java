/**
 * Entity package for the Kube API Client application.
 *
 * <p>
 * This package contains the entity classes that define the data models used within the application.
 * The application is designed to integrate Kubernetes API with Spring Boot, providing a structured
 * approach for managing Kubernetes resources such as secrets.
 * </p>
 *
 * <h2>Key Components</h2>
 * <ul>
 *   <li>{@code SecretEntity}: Represents a Kubernetes secret key-value pair.</li>
 *   <li>{@code SecretsEntity}: Represents a collection of Kubernetes secrets, including their metadata and key-value pairs.</li>
 * </ul>
 *
 * <h2>Features</h2>
 * <ul>
 *   <li>Defines entity models for individual Kubernetes secrets and collections of secrets.</li>
 *   <li>Models are integrated with the Kubernetes API for resource management.</li>
 * </ul>
 *
 * <p>
 * For detailed implementation examples and guidelines, refer to the
 * <a href="https://github.com/bayudwiyansatria/kube-apiclient">GitHub repository</a>.
 * </p>
 *
 * @author Bayu Dwiyan Satria
 * @version 1.0.0
 */
package com.bayudwiyansatria.spring.model.entity;