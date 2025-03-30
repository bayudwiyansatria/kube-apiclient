package com.bayudwiyansatria.spring.service;

import com.bayudwiyansatria.spring.model.Response;
import com.bayudwiyansatria.spring.model.entity.SecretsEntity;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Service interface for managing Kubernetes secrets.
 * <p>
 * This interface defines the contract for operations related to Kubernetes secrets.
 *
 * <p>
 * Implementations of this interface should provide the logic for retrieving and managing secrets.
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
public interface SecretService {

    /**
     * Retrieves a list of all Kubernetes secrets.
     *
     * @return a list of SecretsEntity representing the secrets
     */
    Response<List<SecretsEntity>> getSecrets();
}
