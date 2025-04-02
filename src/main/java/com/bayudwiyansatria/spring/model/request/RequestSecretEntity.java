package com.bayudwiyansatria.spring.model.request;

import com.bayudwiyansatria.spring.model.entity.secrets.SecretEntity;
import java.util.List;
import lombok.Data;

/**
 * RequestSecretEntity
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@Data
public class RequestSecretEntity {

    /**
     * The namespace in which the Kubernetes secret is located.
     */
    private String namespace;

    /**
     * The name of the Kubernetes secret.
     */
    private String name;

    /**
     * The type of the Kubernetes secret. Default is "Opaque".
     */
    private String type = "Opaque";

    /**
     * The type of the Kubernetes secret.
     */
    private List<SecretEntity> data;
}
