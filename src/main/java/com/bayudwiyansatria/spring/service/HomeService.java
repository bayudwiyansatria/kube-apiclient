package com.bayudwiyansatria.spring.service;

import com.bayudwiyansatria.spring.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Service class for handling operations related to the Home entity.
 * <p>
 * This class provides functionality for the Home service. It is annotated with {@link Service} to
 * indicate that it is a Spring-managed service bean.
 * </p>
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
public class HomeService {

    /**
     * Constructor for the {@link HomeService}.
     * <p>
     * This constructor is used for initializing the HomeService class.
     * </p>
     *
     * @since 0.0.1
     */
    public HomeService(
    ) {
    }

    /**
     * Returns a response indicating the success of the operation.
     *
     * <p>
     * This method returns a {@link Response} object with a success message and HTTP status
     * indicating the success of the operation, without any data payload.
     * </p>
     *
     * @return a {@link Response} object containing the success message, HTTP status, and null data
     * @since 0.0.1
     */
    public Response<?> getHome() {
        return new Response<>(
            "Success",
            HttpStatus.OK.value(),
            null
        );
    }
}
