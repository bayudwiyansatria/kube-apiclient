package com.bayudwiyansatria.spring.controller;

import com.bayudwiyansatria.spring.model.Response;
import com.bayudwiyansatria.spring.model.entity.secrets.SecretEntity;
import com.bayudwiyansatria.spring.model.request.RequestSecretEntity;
import com.bayudwiyansatria.spring.service.SecretService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing Kubernetes secrets.
 * <p>
 * This controller provides endpoints to interact with Kubernetes secrets.
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
     * Service layer for managing secrets.
     */
    private final SecretService secretService;

    /**
     * Constructor for SecretController.
     *
     * @param secretService the service layer for managing secrets
     */
    public SecretController(SecretService secretService) {
        this.secretService = secretService;
    }

    /**
     * Endpoint to retrieve all Kubernetes secrets.
     *
     * @return a {@link ResponseEntity} containing the list of secrets
     */
    @GetMapping("")
    public ResponseEntity<?> listSecrets() {
        return ResponseEntity
            .ok(secretService.list(true));
    }

    /**
     * Endpoint to retrieve a specific Kubernetes secret by namespace and name.
     *
     * @param namespace the namespace of the secret
     * @param name      the name of the secret
     * @return a {@link ResponseEntity} containing the secret details
     */
    @GetMapping("/{namespace}/{name}")
    public ResponseEntity<?> getSecret(
        @PathVariable String namespace,
        @PathVariable String name
    ) {
        return ResponseEntity
            .ok(secretService.get(
                namespace,
                name
            ));
    }

    /**
     * Endpoint to retrieve a specific Kubernetes secret by namespace and name.
     *
     * @param request the request object containing the namespace and name of the secret
     * @return a {@link ResponseEntity} containing the secret details
     */
    @PostMapping("")
    public ResponseEntity<?> createSecret(
        @RequestBody RequestSecretEntity request
    ) {
        String namespace = request.getNamespace();
        String name = request.getName();
        String type = request.getType();
        List<SecretEntity> data = request.getData();

        return ResponseEntity
            .ok(secretService.create(
                namespace,
                name,
                type,
                data)
            );
    }

    @PutMapping("/{namespace}/{name}")
    public ResponseEntity<?> updateSecret(
        @PathVariable String namespace,
        @PathVariable String name,
        @RequestBody RequestSecretEntity request
    ) {
        return ResponseEntity
            .ok(new Response<>(
                "Update Secret",
                HttpStatus.NOT_IMPLEMENTED.value(),
                null
            ));
    }

    /**
     * Endpoint to retrieve a specific Kubernetes secret by namespace and name.
     *
     * @param namespace the namespace of the secret
     * @param name      the name of the secret
     * @return a {@link ResponseEntity} containing the secret details
     */
    @DeleteMapping("/{namespace}/{name}")
    public ResponseEntity<?> deleteSecret(
        @PathVariable String namespace,
        @PathVariable String name
    ) {
        return ResponseEntity
            .ok(secretService.deleteSecret(
                namespace,
                name
            ));
    }
}
