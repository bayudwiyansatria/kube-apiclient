package com.bayudwiyansatria.spring.controller;

import com.bayudwiyansatria.spring.model.Response;
import com.bayudwiyansatria.spring.model.entity.secrets.SecretEntity;
import com.bayudwiyansatria.spring.model.request.RequestSecretEntity;
import com.bayudwiyansatria.spring.service.SecretService;
import com.bayudwiyansatria.spring.util.SecretEnum;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SecretController
 * <p>
 * This REST controller manages Kubernetes secrets. It provides endpoints for CRUD operations on
 * Kubernetes secrets such as retrieving, creating, updating, and deleting secrets. Each operation
 * interacts with the {@link SecretService} to perform business logic related to secrets.
 * </p>
 * <p>
 * The class is annotated with {@code @RestController} and {@code @Slf4j} to enable logging and to
 * indicate that this is a Spring MVC controller that handles HTTP requests.
 * </p>
 *
 * <h2>Endpoints:</h2>
 * <ul>
 *   <li><b>GET /api/v1/secret</b>: Retrieves a list of all Kubernetes secrets.</li>
 *   <li><b>GET /api/v1/secret/{namespace}/{name}</b>: Retrieves a specific Kubernetes secret by namespace and name.</li>
 *   <li><b>POST /api/v1/secret</b>: Creates a new Kubernetes secret in the specified namespace.</li>
 *   <li><b>PUT /api/v1/secret/{namespace}/{name}</b>: Updates an existing Kubernetes secret (not implemented yet).</li>
 *   <li><b>DELETE /api/v1/secret/{namespace}/{name}</b>: Deletes a specific Kubernetes secret by namespace and name.</li>
 * </ul>
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/secret")
public class SecretController {

    /**
     * Service layer for managing Kubernetes secrets. This service provides the business logic for
     * creating, retrieving, updating, and deleting secrets.
     */
    private final SecretService secretService;

    /**
     * Constructor for {@link SecretController}.
     *
     * @param secretService the service layer for managing Kubernetes secrets
     * @since 0.0.1
     */
    public SecretController(SecretService secretService) {
        this.secretService = secretService;
    }

    /**
     * Endpoint to retrieve all Kubernetes secrets. This method interacts with the
     * {@link SecretService} to fetch the list of secrets.
     *
     * @return a {@link ResponseEntity} containing the list of secrets
     * @since 0.0.1
     */
    @Operation(
        summary = "Get all secrets",
        description = "Retrieves a list of all Kubernetes secrets"
    )
    @GetMapping("")
    public ResponseEntity<?> list() {
        log.info("Fetching all Kubernetes secrets");
        return ResponseEntity
            .ok(secretService.list(true));
    }

    /**
     * Endpoint to retrieve a specific Kubernetes secret by namespace and name. This method fetches
     * the details of a secret based on the provided namespace and name by calling the
     * {@link SecretService}.
     *
     * @param namespace the namespace of the secret
     * @param name      the name of the secret
     * @return a {@link ResponseEntity} containing the secret details
     * @since 0.0.1
     */
    @GetMapping("/{namespace}/{name}")
    public ResponseEntity<?> get(
        @PathVariable String namespace,
        @PathVariable String name
    ) {
        log.info("Fetching secret with namespace: {} and name: {}", namespace, name);
        return ResponseEntity
            .ok(secretService.get(
                namespace,
                name
            ));
    }

    /**
     * Endpoint to create a new Kubernetes secret in the specified namespace. This method takes the
     * request body containing the secret details and interacts with the {@link SecretService} to
     * create the secret.
     *
     * @param request the request object containing the namespace, name, type, and data for the new
     *                secret
     * @return a {@link ResponseEntity} containing the created secret details
     * @since 0.0.1
     */
    @Operation(
        summary = "Create a new secret",
        description = "Creates a new Kubernetes secret in the specified namespace"
    )
    @PostMapping("")
    public ResponseEntity<?> create(
        @RequestBody RequestSecretEntity request
    ) {
        // Extract the details from the request object
        String namespace = request.getNamespace();
        String name = request.getName();
        String type = request.getType();
        List<SecretEntity> data = request.getData();

        // Log the creation request for debugging purposes
        log.info("Creating secret with namespace: {}, name: {}, type: {}", namespace, name, type);

        // Convert the type into a SecretEnum, handle any invalid types gracefully
        SecretEnum secretTypeEnum;
        try {
            // Assuming the 'type' from the request maps directly to the SecretEnum name
            secretTypeEnum = SecretEnum.valueOf(type);
        } catch (IllegalArgumentException e) {
            // If the type is not valid, handle this case, perhaps with a custom error response
            return ResponseEntity
                .badRequest()
                .body(new Response<>(
                    "Invalid secret type: " + type,
                    HttpStatus.BAD_REQUEST.value(),
                    null
                ));
        }

        // Convert the SecretEnum to the corresponding Kubernetes value
        String kubernetesType = secretTypeEnum.getKubernetesValue();

        // Create the secret using the SecretService
        return ResponseEntity
            .ok(secretService.create(
                namespace,
                name,
                kubernetesType,
                data)
            );
    }

    /**
     * Endpoint to update an existing Kubernetes secret. This method is currently not implemented.
     * In the future, it will interact with the {@link SecretService} to update the secret details
     * based on the provided namespace, name, and updated request.
     *
     * @param namespace the namespace of the secret to be updated
     * @param name      the name of the secret to be updated
     * @param request   the request object containing the updated details of the secret
     * @return a {@link ResponseEntity} indicating the status of the operation
     * @since 0.0.1
     */
    @PutMapping("/{namespace}/{name}")
    public ResponseEntity<?> update(
        @PathVariable String namespace,
        @PathVariable String name,
        @RequestBody RequestSecretEntity request
    ) {
        log.warn("Update secret endpoint is not implemented yet for namespace: {} and name: {}",
            namespace, name);

        return ResponseEntity
            .ok(new Response<>(
                "Update Secret",
                HttpStatus.NOT_IMPLEMENTED.value(),
                null
            ));
    }

    /**
     * Endpoint to delete a specific Kubernetes secret by namespace and name. This method interacts
     * with the {@link SecretService} to delete the specified secret.
     *
     * @param namespace the namespace of the secret to be deleted
     * @param name      the name of the secret to be deleted
     * @return a {@link ResponseEntity} containing the status of the delete operation
     * @since 0.0.1
     */
    @DeleteMapping("/{namespace}/{name}")
    public ResponseEntity<?> deleteSecret(
        @PathVariable String namespace,
        @PathVariable String name
    ) {
        log.info("Deleting secret with namespace: {} and name: {}", namespace, name);
        return ResponseEntity
            .ok(secretService.delete(
                namespace,
                name
            ));
    }
}
