package com.bayudwiyansatria.spring.controller;

import com.bayudwiyansatria.spring.service.SecretService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<Object> getSecrets() {
        return ResponseEntity
            .ok(secretService.getSecrets(true));
    }
}
