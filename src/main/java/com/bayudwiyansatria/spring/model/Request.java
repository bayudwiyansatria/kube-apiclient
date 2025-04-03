package com.bayudwiyansatria.spring.model;

import lombok.Data;

/**
 * Represents a generic request object that holds a request payload.
 * <p>
 * This class is used to encapsulate a request with a generic payload of type {@code T}. It is
 * annotated with {@code @Data} from Lombok, which generates getters, setters, and other utility
 * methods for the class.
 * </p>
 *
 * <p>The {@code request} field holds the payload of the request, which can be of any type.</p>
 *
 * @param <T> the type of the request payload
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@Data
public class Request<T> {

    /**
     * The request payload.
     * <p>
     * This field holds the payload of the request, which can be any type specified by the generic
     * type {@code T}.
     * </p>
     */
    private T request;

    /**
     * Constructs a new Request with the specified request payload.
     *
     * @param request the request payload
     * @since 0.0.1
     */
    public Request(T request) {
        this.request = request;
    }
}
