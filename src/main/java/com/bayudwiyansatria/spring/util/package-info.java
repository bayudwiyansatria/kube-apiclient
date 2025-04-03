/**
 * Utility package for the Kube API Client application.
 *
 * <p>
 * This package contains utility classes that provide various helper functions for handling
 * authentication, error parsing, and custom filters used throughout the Kube API Client
 * application.
 * </p>
 *
 * <h2>Key Components</h2>
 * <ul>
 *   <li>{@link com.bayudwiyansatria.spring.util.ApiKeyAuthentication}: Handles the authentication process using an API key to interact with the Kubernetes API.</li>
 *   <li>{@link com.bayudwiyansatria.spring.util.KubernetesErrorParser}: Provides utilities to parse and handle error responses from the Kubernetes API.</li>
 *   <li>{@link com.bayudwiyansatria.spring.util.ServerApiKeyAuthenticationFilter}: A custom filter for authenticating API requests based on the server's API key.</li>
 * </ul>
 *
 * <h2>Features</h2>
 * <ul>
 *   <li>API key authentication support for securing API endpoints.</li>
 *   <li>Error parsing utilities for improved debugging and logging.</li>
 *   <li>Custom filters to enforce authentication and security policies.</li>
 * </ul>
 *
 * <p>
 * For more information, visit the project's
 * <a href="https://github.com/bayudwiyansatria/kube-apiclient">GitHub repository</a>.
 * </p>
 *
 * @author Bayu Dwiyan Satria
 * @version 1.0.0
 */
package com.bayudwiyansatria.spring.util;