package com.bayudwiyansatria.spring.model.entity.secrets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a Kubernetes secret key-value pair.
 * <p>
 * This class models a single key-value pair of a Kubernetes secret. It is used to store the key and
 * its corresponding value, which are typically used for authentication or configuration purposes in
 * a Kubernetes environment. The class is annotated with Lombok annotations to automatically
 * generate getters, setters, and constructors.
 * </p>
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecretEntity {


    /**
     * The key of the secret.
     * <p>
     * This is the name or identifier of the secret. It is used to reference the secret's value.
     * </p>
     */
    private String key;

    /**
     * The value of the secret.
     * <p>
     * This is the content or value associated with the key. It could be a password, API key, or
     * other sensitive information stored as part of the Kubernetes secret.
     * </p>
     */
    private String value;
}
