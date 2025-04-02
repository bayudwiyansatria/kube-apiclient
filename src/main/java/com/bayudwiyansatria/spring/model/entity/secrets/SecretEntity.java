package com.bayudwiyansatria.spring.model.entity.secrets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a Kubernetes secret key-value pair.
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
     */
    private String key;

    /**
     * The value of the secret.
     */
    private String value;
}
