package com.bayudwiyansatria.spring.service;

import com.bayudwiyansatria.spring.model.Response;
import org.springframework.stereotype.Service;

/**
 * Service interface for managing Kubernetes namespaces.
 * <p>
 * This interface defines the method required to interact with Kubernetes namespaces, particularly
 * for listing the available namespaces.
 * </p>
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
public interface NamespaceService {

    /**
     * Lists all available Kubernetes namespaces.
     * <p>
     * This method retrieves and returns a list of all Kubernetes namespaces. The result is wrapped
     * in a {@link Response} object.
     * </p>
     *
     * @return a {@link Response} object containing a list of namespaces or relevant information.
     * @since 0.0.1
     */
    Response<?> list();
}
