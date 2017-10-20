package com.bellotapps.examples.spring_boot_example.web.security.authentication;

import io.jsonwebtoken.Claims;

/**
 * Defines behaviour for an object than can parse JWT tokens.
 */
public interface JWTParser {

    /**
     * Parses the given token, transforming it into the {@link Claims} set in it.
     *
     * @param jwtToken The token to be parsed.
     * @return The {@link Claims} in the token.
     * @throws IllegalArgumentException If the given {@code jwtToken} is null or if it does not have text.
     */
    Claims parse(String jwtToken) throws IllegalArgumentException;
}
