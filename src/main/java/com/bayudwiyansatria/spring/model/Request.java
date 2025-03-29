package com.bayudwiyansatria.spring.model;

import lombok.Data;

/**
 * Request
 * <p>
 * This class represents a request object that holds a generic request payload. It is annotated with
 * {@code @Data} from Lombok to generate getters, setters, and other utility methods.
 *
 * <p>The {@code request} field holds the request payload.</p>
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@Data
public class Request {

    /**
     * The request payload.
     */
    private Object request;

    /**
     * Constructs a new Request with the specified request payload.
     *
     * @param request the request payload
     * @since 0.0.1
     */
    public Request(Object request) {
        this.request = request;
    }
}
