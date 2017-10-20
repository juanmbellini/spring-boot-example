package com.bellotapps.examples.spring_boot_example.web.security.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.HashSet;

/**
 * An {@link Authentication} created with a JWT token.
 */
/* package */ class JwtAuthenticationToken extends AbstractAuthenticationToken {

    /**
     * The jwt token in string representation.
     * Will be considered the credentials in this token.
     */
    private final String rawToken;

    /**
     * The username of the user that this token belongs to.
     * Will be considered the principal in this token.
     */
    private String username;

    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param rawToken The raw jwt token.
     */
    /* package */ JwtAuthenticationToken(String rawToken) {
        super(new HashSet<>());
        this.rawToken = rawToken;
    }

    /**
     * Default constructor (no credentials are stored).
     */
    /* package */ JwtAuthenticationToken() {
        this(null);
    }


    /**
     * Type safe method for getting the credentials of this token.
     *
     * @return The raw jwt token stored in this {@link Authentication}.
     */
    /* package */ String getRawToken() {
        return (String) getCredentials();
    }

    /**
     * Sets the principal fot this token.
     *
     * @param principal The principal to be set.
     */
    /* package */ void setPrincipal(String principal) {
        this.username = principal;
    }

    /* package */ void authenticate() {
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return rawToken;
    }

    @Override
    public Object getPrincipal() {
        if (!isAuthenticated()) {
            throw new IllegalStateException("Not yet authenticated");
        }
        return username;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (!authenticated && isAuthenticated()) {
            throw new IllegalStateException("Can't undo authentication");
        }
        super.setAuthenticated(authenticated);
    }
}
