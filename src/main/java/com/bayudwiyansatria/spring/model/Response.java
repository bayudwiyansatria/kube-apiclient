package com.bayudwiyansatria.spring.model;

import lombok.Data;

/**
 * Response
 * <p>
 * This class represents a response object that holds a message, status, and data. It is annotated
 * with {@code @Data} from Lombok to generate getters, setters, and other utility methods.
 * </p>
 *
 * <p>The {@code message} field holds the response message, which provides information about the
 * result of the operation.</p>
 * <p>The {@code status} field indicates the success or failure of the response, typically using
 * HTTP status codes.</p>
 * <p>The {@code data} field holds the actual response payload, which can be of any type.</p>
 *
 * @param <T> the type of the data in the response
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@Data
public class Response<T> {

    /**
     * The response message.
     * <p>
     * This field holds the message providing information about the outcome of the request, such as
     * success or failure.
     * </p>
     */
    private String message;

    /**
     * The status of the response.
     * <p>
     * This field indicates the status of the response, such as an HTTP status code. A typical value
     * is a code like 200 for success or 400 for failure.
     * </p>
     */
    private int status;

    /**
     * The additional data related to the response.
     * <p>
     * This field holds the actual payload of the response, which can be of any type, determined by
     * the generic type {@code T}.
     * </p>
     */
    private T data;

    /**
     * Constructs a new Response with the specified message, status, and data.
     * <p>
     * This constructor initializes the {@code message}, {@code status}, and {@code data} fields
     * with the given values.
     * </p>
     *
     * @param message the response message
     * @param status  the status of the response
     * @param data    the additional data related to the response
     * @since 0.0.1
     */
    public Response(
        String message,
        int status,
        T data
    ) {
        this.message = message;
        this.status = status;
        this.data = data;
    }
}
