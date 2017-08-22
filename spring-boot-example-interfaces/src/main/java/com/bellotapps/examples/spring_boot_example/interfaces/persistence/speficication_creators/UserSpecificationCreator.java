package com.bellotapps.examples.spring_boot_example.interfaces.persistence.speficication_creators;

import com.bellotapps.examples.spring_boot_example.models.User;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

/**
 * Defines behaviour of an object in charge of creating {@link Specification}s of {@link User}s.
 */
public interface UserSpecificationCreator {

    /**
     * Creates a new {@link Specification} of {@link User} used to query them according to the given parameters,
     * applying ANDs between them.
     * String parameters are compared with the "like" keyword, matching anywhere.
     *
     * @param fullName     A filter for the {@link User}'s full name.
     * @param minBirthDate The minimum age of the retrieved {@link User}s
     * @param maxBirthDate The maximum age of the retrieved {@link User}s
     * @param username     A filter for the {@link User}'s username.
     * @param eMail        A filter for the {@link User}'s email.
     * @return The {@link Specification} of {@link User}
     * that can be used to get those {@link User}s matching the give parameters.
     * @apiNote Those parameter that are {@code null} must not be taken into account in the {@link Specification}.
     */
    Specification<User> create(String fullName, LocalDate minBirthDate, LocalDate maxBirthDate,
                               String username, String eMail);
}
