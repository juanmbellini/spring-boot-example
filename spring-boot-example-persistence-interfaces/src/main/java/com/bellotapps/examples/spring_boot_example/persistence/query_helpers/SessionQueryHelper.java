package com.bellotapps.examples.spring_boot_example.persistence.query_helpers;

import com.bellotapps.examples.spring_boot_example.exceptions.InvalidPropertiesException;
import com.bellotapps.examples.spring_boot_example.models.Session;
import com.bellotapps.examples.spring_boot_example.models.User;
import org.springframework.data.domain.Pageable;

/**
 * Defines behaviour of an object in charge of helping the task of querying {@link User}s
 * by a {@link com.bellotapps.examples.spring_boot_example.persistence.daos.UserDao}.
 */
public interface SessionQueryHelper {

    /**
     * Validates that the given {@link Pageable} is valid for querying {@link Session}s.
     *
     * @param pageable The {@link Pageable} to be validated.
     * @throws InvalidPropertiesException If it has a {@link org.springframework.data.domain.Sort}
     *                                    with invalid properties.
     */
    void validatePageable(Pageable pageable) throws InvalidPropertiesException;
}
