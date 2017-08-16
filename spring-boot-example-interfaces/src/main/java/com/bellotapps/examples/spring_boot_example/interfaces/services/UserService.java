package com.bellotapps.examples.spring_boot_example.interfaces.services;

import com.bellotapps.examples.spring_boot_example.models.User;

import java.util.List;
import java.util.Optional;

/**
 * Defines behaviour of the service in charge of managing {@link User}s.
 */
public interface UserService {

    /**
     * @return The {@link List} of {@link User}s registered in the application.
     */
    List<User> getAll();

    /**
     * Gets a specific {@link User} by it's {@code id}.
     *
     * @param id The {@link User}'s id.
     * @return A <b>nullable</b> {@link Optional} of {@link User}
     * containing the {@link User} with the given {@code id} if it exists, or {@code null} otherwise.
     */
    Optional<User> getById(long id);
}