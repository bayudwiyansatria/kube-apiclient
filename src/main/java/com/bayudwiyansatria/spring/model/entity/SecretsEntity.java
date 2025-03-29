package com.bayudwiyansatria.spring.model.entity;

import com.bayudwiyansatria.spring.model.entity.secrets.SecretEntity;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a collection of Kubernetes secrets.
 * <p>
 * This class contains the name of the secret and a list of key-value pairs
 * representing the individual secrets.
 * 
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecretsEntity {

    /**
     * The name of the Kubernetes secret.
     */
    private String name;

    /**
     * A list of key-value pairs representing the secrets.
     */
    private List<SecretEntity> secret;
}
