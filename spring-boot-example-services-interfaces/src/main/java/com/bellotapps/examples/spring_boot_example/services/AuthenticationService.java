package com.bellotapps.examples.spring_boot_example.services;

import com.bellotapps.examples.spring_boot_example.exceptions.InvalidCredentialsException;
import com.bellotapps.examples.spring_boot_example.models.User;

/**
 * Defines behaviour of the service in charge of managing authentication.
 */
public interface AuthenticationService {

    /**
     * Performs the login process (i.e username and password authentication),
     * returning a token that represents the session.
     *
     * @param username The user's username.
     * @param password The user's password.
     * @return A JWT in string representation.
     * @throws InvalidCredentialsException In cae the user does not exists, or if the password is not the correct one.
     */
    UserAndTokenContainer login(String username, String password) throws InvalidCredentialsException;

    /**
     * Class wrapping a {@link User} and a token (in {@link String} representation) for it.
     */
    class UserAndTokenContainer {

        /**
         * The wrapped user.
         */
        private final User user;
        /**
         * The token belonging to the {@link User}.
         */
        private final String token;

        /**
         * Constructor.
         *
         * @param user  The wrapped user.
         * @param token The token belonging to the {@link User}.
         */
        /* package */ UserAndTokenContainer(User user, String token) {
            this.user = user;
            this.token = token;
        }

        /**
         * @return The wrapped user.
         */
        public User getUser() {
            return user;
        }

        /**
         * @return The token belonging to the {@link User}.
         */
        public String getToken() {
            return token;
        }
    }
}
