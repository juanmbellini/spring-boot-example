package com.bellotapps.examples.spring_boot_example.persistence;

import com.bellotapps.examples.spring_boot_example.interfaces.persistence.speficication_creators.UserSpecificationCreator;
import com.bellotapps.examples.spring_boot_example.models.User;
import org.hibernate.criterion.MatchMode;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Concrete implementation of a {@link UserSpecificationCreator}.
 */
@Component
public class UserSpecificationCreatorImpl implements UserSpecificationCreator {

    @Override
    public Specification<User> create(String fullName, LocalDate minBirthDate, LocalDate maxBirthDate,
                                      String username, String eMail) {
        return (root, query, cb) -> {
            final List<Predicate> predicates = new LinkedList<>();
            // Filter full name
            Optional.ofNullable(fullName)
                    .map(str -> PersistenceHelper
                            .toLikePredicate(cb, root, "fullName", str, MatchMode.ANYWHERE, false))
                    .ifPresent(predicates::add);
            // Filter birth date
            final Path<LocalDate> birthDatePath = root.get(root.getModel()
                    .getDeclaredSingularAttribute("birthDate", LocalDate.class));
            Optional.ofNullable(minBirthDate).map(date -> cb.greaterThanOrEqualTo(birthDatePath, date))
                    .ifPresent(predicates::add);
            Optional.ofNullable(maxBirthDate).map(date -> cb.lessThanOrEqualTo(birthDatePath, date))
                    .ifPresent(predicates::add);
            // Filter username
            Optional.ofNullable(username)
                    .map(str -> PersistenceHelper
                            .toLikePredicate(cb, root, "username", str, MatchMode.ANYWHERE, false))
                    .ifPresent(predicates::add);
            // Filter email
            Optional.ofNullable(eMail)
                    .map(str -> PersistenceHelper
                            .toLikePredicate(cb, root, "email", str, MatchMode.ANYWHERE, false))
                    .ifPresent(predicates::add);

            return predicates.stream().reduce(cb.and(), cb::and);
        };
    }
}
