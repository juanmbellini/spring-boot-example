package com.bellotapps.examples.spring_boot_example.interfaces.daos;

import com.bellotapps.examples.spring_boot_example.models.User;

import java.util.Optional;

/**
 * Defines behaviour of the DAO in charge of managing {@link User}s data.
 */
public interface UserDao extends FilterableJpaRepository<User, Long> {

    /**
     * Retrieves the {@link User} with the given {@code username}.
     *
     * @param username The {@link User}'s username.
     * @return A <b>nullable</b> {@link Optional} of {@link User}
     * containing the {@link User} with the given {@code username} if it exists, or {@code null} otherwise.
     */
    Optional<User> findByUsername(String username);

    /**
     * Retrieves the {@link User} with the given {@code email}.
     *
     * @param email The {@link User}'s email.
     * @return A <b>nullable</b> {@link Optional} of {@link User}
     * containing the {@link User} with the given {@code email} if it exists, or {@code null} otherwise.
     */
    Optional<User> findByEmail(String email);

}
