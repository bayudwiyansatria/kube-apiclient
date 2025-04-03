/**
 * Configuration package for the Kubernetes API Client Spring Boot application.
 *
 * <p>
 * This package contains configuration classes that set up various aspects of the application,
 * including but not limited to:
 * </p>
 *
 * <ul>
 *   <li>OpenAPI/Swagger documentation configuration</li>
 *   <li>Security configurations</li>
 *   <li>API endpoint configurations</li>
 *   <li>Authentication and authorization settings</li>
 * </ul>
 *
 * <p>
 * The application is designed to provide a structured starting point for integrating Kubernetes API
 * with Spring Boot applications. It includes pre-configured dependencies, build scripts, and best
 * practices for managing Kubernetes resources such as secrets.
 * </p>
 *
 * <h2>Key Components</h2>
 * <ul>
 *   <li>{@code OpenAPIConfig}: Configures API documentation with Swagger/OpenAPI 3.0</li>
 *   <li>{@code SecurityConfig}: Handles API security and authentication</li>
 *   <li>{@code WebConfig}: Manages web-related configurations</li>
 * </ul>
 *
 * <h2>Features</h2>
 * <ul>
 *   <li>API Key authentication support</li>
 *   <li>Kubernetes client integration</li>
 *   <li>Customizable security headers</li>
 *   <li>Comprehensive API documentation</li>
 *   <li>Spring Boot auto-configuration</li>
 * </ul>
 *
 * <h2>Dependencies</h2>
 * <ul>
 *   <li>Spring Boot 3.x</li>
 *   <li>Kubernetes Java Client</li>
 *   <li>SpringDoc OpenAPI UI</li>
 * </ul>
 *
 * <p>
 * For more information, visit the project's
 * <a href="https://github.com/bayudwiyansatria/kube-apiclient">GitHub repository</a>.
 * </p>
 *
 * @author Bayu Dwiyan Satria
 * @version 1.0.0
 * @since 1.0.0
 */
package com.bayudwiyansatria.spring.config;