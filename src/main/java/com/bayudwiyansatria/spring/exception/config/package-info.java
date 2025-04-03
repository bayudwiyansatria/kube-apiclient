/**
 * Provides configuration exception handling and error management for the Kubernetes API client
 * integration.
 *
 * <p>This package contains specialized exception classes and handlers designed to manage errors
 * that occur during Kubernetes API operations, particularly focusing on configuration and
 * integration issues.</p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *   <li>Custom exception handling for Kubernetes API operations</li>
 *   <li>Standardized error response formatting</li>
 *   <li>Configuration validation exceptions</li>
 *   <li>Integration error handling for:
 *     <ul>
 *       <li>Authentication failures</li>
 *       <li>Authorization issues</li>
 *       <li>Resource not found scenarios</li>
 *       <li>API version mismatches</li>
 *       <li>Configuration parsing errors</li>
 *     </ul>
 *   </li>
 *   <li>Detailed error messages with debugging information</li>
 *   <li>Integration with Spring's exception handling mechanism</li>
 * </ul>
 *
 * <h2>Usage Example:</h2>
 * <pre>
 * try {
 *     // Kubernetes API operation
 * } catch (KubernetesConfigurationException e) {
 *     // Handle configuration-specific errors
 * } catch (KubernetesException e) {
 *     // Handle general Kubernetes API errors
 * }
 * </pre>
 *
 * <h2>Package Structure:</h2>
 * <ul>
 *   <li>{@code KubernetesException} - Base exception class for Kubernetes errors</li>
 *   <li>{@code KubernetesConfigurationException} - Specific to configuration errors</li>
 *   <li>{@code KubernetesErrorHandler} - Central error handling and processing</li>
 * </ul>
 *
 * @author Bayu Dwiyan Satria
 * @version 1.0.0
 */
package com.bayudwiyansatria.spring.exception.config;
