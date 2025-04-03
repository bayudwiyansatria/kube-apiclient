package com.bayudwiyansatria.spring.util;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * An Authentication object representing an API Key for Spring Security. Extends the
 * {@link AbstractAuthenticationToken} class to provide a custom implementation for API Key-based
 * authentication.
 *
 * <p>
 * This class is used to authenticate requests that include an API Key in the request header, which
 * is processed and authenticated by Spring Security. The API Key itself serves as the principal for
 * the authentication, while the authorities (roles/permissions) associated with the API Key are
 * provided as granted authorities.
 * </p>
 *
 * <p>
 * API Key authentication is commonly used in stateless applications where user credentials are not
 * needed and an API Key can uniquely identify and authenticate a user or system.
 * </p>
 *
 * @author Bayu Dwiyan Satria
 * @version 0.0.1
 * @since 0.0.1
 */
public class ApiKeyAuthentication extends AbstractAuthenticationToken {

    /**
     * The API Key value obtained from the request header.
     */
    private final String apiKey;

    /**
     * Creates a new {@link ApiKeyAuthentication} object with the provided API Key and authorities.
     * <p>
     * The constructor initializes the {@link ApiKeyAuthentication} object with the given API Key
     * and assigns the provided authorities to the authentication token.
     * </p>
     *
     * @param apiKey      the API Key value obtained from the request header
     * @param authorities the collection of {@link GrantedAuthority} objects associated with the API
     *                    Key, typically an empty collection (e.g.,
     *                    {@link AuthorityUtils#NO_AUTHORITIES})
     */
    public ApiKeyAuthentication(
        String apiKey,
        Collection<? extends GrantedAuthority> authorities
    ) {
        super(authorities);
        this.apiKey = apiKey;
        setAuthenticated(true);
    }

    /**
     * Returns null as credentials are not applicable for API Key authentication.
     * <p>
     * In API Key authentication, the credentials (password or sensitive data) are not required. The
     * {@code apiKey} is the principal in this case.
     * </p>
     *
     * @return null, as credentials are not used
     */
    @Override
    public Object getCredentials() {
        return null;
    }

    /**
     * Returns the API Key value as the principal object.
     * <p>
     * The principal is typically the entity that is authenticated, which in this case is the API
     * Key string that identifies the requester.
     * </p>
     *
     * @return the API Key string, which is the principal for authentication
     */
    @Override
    public Object getPrincipal() {
        return apiKey;
    }
}
