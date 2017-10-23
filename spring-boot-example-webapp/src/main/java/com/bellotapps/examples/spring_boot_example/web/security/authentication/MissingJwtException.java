package com.bellotapps.examples.spring_boot_example.web.security.authentication;

import org.springframework.security.core.AuthenticationException;

/**
 * Exception to be thrown in case the JWT token is not present.
 */
/* package */ class MissingJwtException extends AuthenticationException {

    /**
     * Default constructor.
     */
    MissingJwtException() {
        super("The JWT token is missing");
    }
}
