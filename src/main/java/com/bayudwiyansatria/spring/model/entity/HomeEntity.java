package com.bayudwiyansatria.spring.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * HomeEntity
 * <p>
 * This class represents the Home entity with fields for id and message. It is annotated with
 * {@code @Entity} to indicate that it is a JPA entity. The {@code @Data} annotation from Lombok
 * generates getters, setters, and other utility methods.
 *
 * <p>The {@code id} field is the primary key and is generated automatically.</p>
 *
 * <p>The {@code message} field represents the message associated with the Home entity.</p>
 *
 * <p>The {@code DOCUMENT_NAME} constant defines the name of the MongoDB collection.</p>
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@Data
@Entity
@Document(
    collection = HomeEntity.DOCUMENT_NAME
)
public class HomeEntity {

    public static final String DOCUMENT_NAME = "test";

    /**
     * The unique identifier for the Home entity. It is generated automatically.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The message associated with the Home entity.
     */
    private String message;

    @Override
    public String toString() {
        return String.format("User: %s %s", id, message);
    }
}
