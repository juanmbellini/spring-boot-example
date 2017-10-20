package com.bellotapps.examples.spring_boot_example.web.security.authentication;

import io.jsonwebtoken.Claims;

/**
 * Defines behaviour for an object than can parse JWT tokens.
 */
public interface JwtParser {

    /**
     * Parses the given token, transforming it into the {@link Claims} set in it.
     *
     * @param jwtToken The token to be parsed.
     * @return The {@link Claims} in the token.
     * @throws IllegalArgumentException If the given {@code jwtToken} is null or if it does not have text.
     */
    JwtTokenData parse(String jwtToken) throws IllegalArgumentException;

    /**
     * Container class wrapping data in a JWT.
     */
    class JwtTokenData {

        /**
         * The user id set in the JWT.
         */
        private final long userId;
        /**
         * The username set in the JWT.
         */
        private final String username;

        /**
         * Constructor.
         *
         * @param userId   The user id set in the JWT.
         * @param username The username set in the JWT.
         */
        /* package */ JwtTokenData(long userId, String username) {
            this.userId = userId;
            this.username = username;
        }

        /**
         * @return The user id set in the JWT.
         */
        public long getUserId() {
            return userId;
        }

        /**
         * @return The username set in the JWT.
         */
        public String getUsername() {
            return username;
        }
    }
}
