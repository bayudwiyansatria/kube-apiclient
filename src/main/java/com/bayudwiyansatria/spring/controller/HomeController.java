package com.bayudwiyansatria.spring.controller;

import com.bayudwiyansatria.spring.model.Response;
import com.bayudwiyansatria.spring.service.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HomeController
 * <p>
 * This class handles HTTP requests for the home endpoint. It is annotated with
 * {@code @RestController} to indicate that it is a Spring MVC controller. It is also annotated with
 * {@code @Slf4j} to enable logging.
 *
 * <p>The {@code getUserById} method handles HTTP GET requests to the root endpoint.</p>
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
     * Home Service
     */
    private final HomeService homeService;

    /**
     * Constructs a new HomeController with the specified HomeService.
     *
     * @param homeService the HomeService to be used by this controller
     * @since 0.0.1
     */
    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    /**
     * Handles HTTP GET requests to the root endpoint. This method returns a ResponseEntity
     * containing a Response object.
     *
     * @return a ResponseEntity with a Response object
     * @since 0.0.1
     */
    @GetMapping("")
    public ResponseEntity<Response> getUserById(
    ) {
        return ResponseEntity
            .ok()
            .body(this.homeService.getHome());
    }
}
