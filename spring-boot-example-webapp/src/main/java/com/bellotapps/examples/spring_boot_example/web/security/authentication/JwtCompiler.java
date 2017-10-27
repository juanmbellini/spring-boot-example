package com.bellotapps.examples.spring_boot_example.web.security.authentication;

import com.bellotapps.examples.spring_boot_example.models.Role;

import java.util.Collections;
import java.util.Set;

/**
 * Defines behaviour for an object than can compile JWT tokens.
 */
public interface JwtCompiler {

    /**
     * Compiles the given token, transforming it into the {@link JwtTokenData} with information taken from the token.
     *
     * @param rawToken The token to be compiled.
     * @return The {@link JwtTokenData} compiled from the token.
     * @throws IllegalArgumentException If the given {@code rawToken} is null or if it does not have text.
     * @throws JwtException             If the compilation process failed
     *                                  (i.e malformed token, expired token, wrong signature, blacklisted, etc.).
     */
    JwtTokenData compile(String rawToken) throws IllegalArgumentException, JwtException;

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
         * The user's granted authorities.
         */
        private final Set<Role> roles;

        /**
         * Constructor.
         *
         * @param userId   The user id set in the JWT.
         * @param username The username set in the JWT.
         * @param roles    The user's granted authorities.
         */
        /* package */ JwtTokenData(long userId, String username, Set<Role> roles) {
            this.userId = userId;
            this.username = username;
            this.roles = roles;
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

        /**
         * @return The user's granted authorities.
         */
        public Set<Role> getRoles() {
            return Collections.unmodifiableSet(roles);
        }
    }
}
