package com.bayudwiyansatria.spring.model;

import lombok.Data;

/**
 * Response
 * <p>
 * This class represents a response object that holds a message, status, and data. It is annotated
 * with {@code @Data} from Lombok to generate getters, setters, and other utility methods.
 *
 * <p>The {@code message} field holds the response message.</p>
 * <p>The {@code status} field indicates the success or failure of the response.</p>
 * <p>The {@code data} field holds the response payload.</p>
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@Data
public class Response<T> {

    /**
     * The response message.
     */
    private String message;

    /**
     * The status of the response.
     */
    private boolean status;

    /**
     * The additional data related to the response.
     */
    private T data;

    /**
     * Constructs a new Response with the specified message, status, and data.
     *
     * @param message the response message
     * @param status  the status of the response
     * @param data    data the additional data related to the response
     */
    public Response(
        String message,
        boolean status,
        T data
    ) {
        this.message = message;
        this.status = status;
        this.data = data;
    }
}
