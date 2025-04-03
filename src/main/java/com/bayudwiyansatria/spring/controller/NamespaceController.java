package com.bayudwiyansatria.spring.controller;

import com.bayudwiyansatria.spring.service.NamespaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * NamespaceController
 * <p>
 * This class handles HTTP requests related to the namespace functionality within the application.
 * It is annotated with {@code @RestController} to indicate that it is a RESTful controller and
 * {@code @Slf4j} to enable logging functionality.
 * </p>
 *
 * <p>The class is responsible for handling GET requests to the "/api/v1/namespace" endpoint
 * and interacting with the {@code NamespaceService} to return a list of namespaces.</p>
 *
 * <p>
 * The {@code getNamespaceService} method processes GET requests and returns a response from the
 * {@code NamespaceService}. The response is returned in the form of a {@link ResponseEntity}.
 * </p>
 *
 * <h2>Endpoints:</h2>
 * <ul>
 *   <li><b>GET /api/v1/namespace</b>: This endpoint returns a list of namespaces by calling
 *   the {@code NamespaceService}.</li>
 * </ul>
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/namespace")
public class NamespaceController {

    /**
     * The NamespaceService that provides business logic for managing namespaces.
     */
    private final NamespaceService namespaceService;

    /**
     * Constructs a new {@code NamespaceController} with the specified {@code NamespaceService}.
     *
     * <p>This constructor injects the {@code NamespaceService} dependency into the controller.
     * The {@code NamespaceService} contains the logic for handling namespace-related
     * operations.</p>
     *
     * @param namespaceService the NamespaceService to be used by this controller
     * @since 0.0.1
     */
    public NamespaceController(NamespaceService namespaceService) {
        this.namespaceService = namespaceService;
    }

    /**
     * Handles HTTP GET requests to the "/api/v1/namespace" endpoint. This method interacts with the
     * {@code NamespaceService} to retrieve the list of namespaces and returns it in the response
     * body wrapped inside a {@code ResponseEntity}.
     *
     * <p>The method returns a {@code ResponseEntity} containing the list of namespaces,
     * indicating success with HTTP status 200.</p>
     *
     * @return a {@link ResponseEntity} containing the list of namespaces from
     * {@code NamespaceService}
     * @since 0.0.1
     */
    @GetMapping("")
    public ResponseEntity<?> getNamespaceService() {

        // Log the request to provide traceability
        log.info("Handling request to get list of namespaces");

        // Return the response entity with the list of namespaces
        return ResponseEntity
            .ok(this.namespaceService.list());
    }
}
