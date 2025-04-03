package com.bayudwiyansatria.spring.controller;

import com.bayudwiyansatria.spring.service.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HomeController
 * <p>
 * This class is a Spring MVC controller that handles HTTP requests for the home endpoint. It is
 * annotated with {@code @RestController} to indicate that it is a RESTful controller, and it uses
 * {@code @Slf4j} to enable logging functionality.
 * </p>
 *
 * <p>The class is responsible for handling GET requests to the root URL ("/") and interacting
 * with the HomeService to return a response to the client.</p>
 *
 * <p>
 * The {@code getUserById} method processes GET requests to the root endpoint and returns a response
 * from the {@code HomeService}. It utilizes {@link ResponseEntity} to provide an appropriate HTTP
 * response to the client.
 * </p>
 *
 * <h2>Endpoints:</h2>
 * <ul>
 *   <li><b>GET /</b>: This endpoint returns a response from the {@code HomeService}.</li>
 * </ul>
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@RestController
@RequestMapping("/")
@Slf4j
public class HomeController {

    /**
     * The HomeService that provides business logic for this controller.
     */
    private final HomeService homeService;

    /**
     * Constructs a new {@code HomeController} with the specified {@code HomeService}.
     *
     * <p>This constructor injects the {@code HomeService} dependency into the controller.
     * The {@code HomeService} contains the logic for handling home-related operations.</p>
     *
     * @param homeService the HomeService to be used by this controller
     * @since 0.0.1
     */
    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    /**
     * Handles HTTP GET requests to the root endpoint ("/"). This method interacts with the
     * {@code HomeService} to retrieve the home data and returns it in the response body wrapped
     * inside a {@code ResponseEntity}.
     *
     * <p>The method returns a {@code ResponseEntity} containing the response from
     * {@code HomeService}, indicating success with HTTP status 200.</p>
     *
     * @return a {@link ResponseEntity} containing the response from {@code HomeService}
     * @since 0.0.1
     */
    @GetMapping("")
    public ResponseEntity<?> getUserById(
    ) {
        // Log the request to provide traceability
        log.info("Handling request to get home data");

        // Return the response entity with the home data
        return ResponseEntity
            .ok()
            .body(this.homeService.getHome());
    }
}
