package com.bellotapps.examples.spring_boot_example.security;

import com.bellotapps.examples.spring_boot_example.models.User;

/**
 * Defines behaviour of an object that is in charge of generating tokens.
 */
public interface TokenGenerator {

    /**
     * Generates a token based on the given {@link User}.
     *
     * @param user The {@link User} to which the generated token belongs to.
     * @return The generated token.
     */
    String generate(final User user);
}
