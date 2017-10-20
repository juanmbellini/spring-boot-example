package com.bellotapps.examples.spring_boot_example.web.security.authentication;

import org.springframework.security.core.AuthenticationException;

/**
 * Exception to be thrown in case the authentication is not present,
 * or if the authentication scheme is not the correct one..
 */
/* package */ class MissingJwtException extends AuthenticationException {

    /**
     * Default constructor.
     */
    MissingJwtException(String authenticationHeader) {
        super("Missing " + authenticationHeader + " header, or wrong authentication scheme");
    }
}
