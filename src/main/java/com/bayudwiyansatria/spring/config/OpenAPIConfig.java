package com.bayudwiyansatria.spring.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPIConfig
 *
 * <p>
 * Configuration class for setting up OpenAPI (Swagger) documentation for the API. This class
 * configures the OpenAPI specification, including metadata such as API title, version, description,
 * contact information, license details, and security configurations (e.g., authentication scheme).
 * </p>
 *
 * <p>
 * The configuration is designed to enable API documentation generation using Swagger, allowing
 * developers and consumers to interact with the API's endpoints easily. It includes authentication
 * using an API key in the header for secure access to the API.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *   <li>API Information: Title, Version, Description</li>
 *   <li>Contact Information: API author’s name, email, and URL</li>
 *   <li>License Information: License name and URL</li>
 *   <li>Security Scheme: API Key-based authentication for secure access</li>
 * </ul>
 *
 * <h2>Security</h2>
 * <p>
 * The class configures an API Key security scheme, requiring an API key in the request header for authentication.
 * </p>
 *
 * @author Bayu Dwiyan Satria
 * @version 1.0.0
 */
@Configuration
public class OpenAPIConfig {

    /**
     * The header key used for API authentication.
     * <p>
     * This value is injected from the application properties to specify the header key for API key
     * authentication.
     * </p>
     */
    @Value("${spring.application.api.auth-token.key}")
    private String principalRequestHeader;

    /**
     * The name of the license for the API.
     * <p>
     * This value is injected from the application properties to define the license under which the
     * API is distributed.
     * </p>
     */
    @Value("${spring.application.license.name}")
    private String licenseName;

    /**
     * The URL of the license for the API.
     * <p>
     * This value is injected from the application properties to provide a URL to the API license
     * information.
     * </p>
     */
    @Value("${spring.application.license.url}")
    private String licenseUrl;

    /**
     * Configures the OpenAPI specification for the API.
     * <p>
     * This method defines various aspects of the OpenAPI documentation, including:
     * </p>
     *
     * <ul>
     *     <li>Basic API Information (title, version, description)</li>
     *     <li>Contact Information (author's details)</li>
     *     <li>License Information (API license details)</li>
     *     <li>Security Requirements (API key-based authentication)</li>
     * </ul>
     *
     * <p>
     * The method creates and returns an {@link OpenAPI} instance that is used by Swagger to generate the API documentation.
     * </p>
     *
     * @return Configured {@link OpenAPI} instance with security, contact, and license details
     * @see OpenAPI
     * @see SecurityScheme
     * @see SecurityRequirement
     * @since 1.0.0
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Kubernetes API Client")
                .version("1.0.0")
                .description("API documentation for Kubernetes API Client")
                .contact(
                    new Contact()
                        .name("Bayu Dwiyan Satria")
                        .email("bayudwiyansatria@gmail.com")
                        .url("https://github.com/bayudwiyansatria")
                )
                .license(
                    new License()
                        .name(this.licenseName)
                        .url(this.licenseUrl)
                )
            )
            .addSecurityItem(
                new SecurityRequirement()
                    .addList(this.principalRequestHeader)
            )
            .components(
                new Components()
                    .addSecuritySchemes(Type.APIKEY.toString(),
                        new SecurityScheme()
                            .type(SecurityScheme.Type.APIKEY)
                            .in(SecurityScheme.In.HEADER)
                            .name(this.principalRequestHeader)
                            .description("Authentication API Key")
                    )
            );
    }
}
