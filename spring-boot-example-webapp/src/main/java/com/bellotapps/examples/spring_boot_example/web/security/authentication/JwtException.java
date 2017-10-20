package com.bellotapps.examples.spring_boot_example.web.security.authentication;

import com.bellotapps.examples.spring_boot_example.exceptions.UnauthenticatedException;

/**
 * Exception thrown when there are JWT issues (e.g invalid token, expired token, etc).
 */
public class JwtException extends UnauthenticatedException {

    /**
     * Default constructor.
     */
    public JwtException() {
        super();
    }

    /**
     * Constructor which can set a {@code message}.
     *
     * @param message The detail message, which is saved for later retrieval by the {@link #getMessage()} method.
     */
    public JwtException(String message) {
        super(message);
    }

    /**
     * Constructor which can set a mes{@code message} and a {@code cause}.
     *
     * @param message The detail message, which is saved for later retrieval by the {@link #getMessage()} method.
     * @param cause   The cause (which is saved for later retrieval by the {@link #getCause()} method).
     *                For more information, see {@link RuntimeException#RuntimeException(Throwable)}.
     */
    public JwtException(String message, Throwable cause) {
        super(message, cause);
    }
}
