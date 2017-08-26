package com.bellotapps.examples.spring_boot_example.persistence;

import com.bellotapps.examples.spring_boot_example.exceptions.InvalidPropertiesException;
import com.bellotapps.examples.spring_boot_example.interfaces.persistence.query_helpers.UserQueryHelper;
import com.bellotapps.examples.spring_boot_example.models.User;
import org.hibernate.criterion.MatchMode;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Concrete implementation of a {@link UserQueryHelper}.
 */
@Component
public class UserQueryHelperImpl implements UserQueryHelper {

    @Override
    public Specification<User> createUserSpecification(String fullName, LocalDate minBirthDate, LocalDate maxBirthDate,
                                                       String username, String email) {
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
            Optional.ofNullable(email)
                    .map(str -> PersistenceHelper
                            .toLikePredicate(cb, root, "email", str, MatchMode.ANYWHERE, false))
                    .ifPresent(predicates::add);

            return predicates.stream().reduce(cb.and(), cb::and);
        };
    }

    @Override
    public void validatePageable(Pageable pageable) throws InvalidPropertiesException {
        final Sort sort = pageable.getSort();
        if (sort == null) {
            return;
        }
        final Set<String> properties = Arrays.stream(User.class.getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.toSet());

        final List<String> invalidProperties = StreamSupport.stream(Spliterators
                .spliteratorUnknownSize(sort.iterator(), Spliterator.ORDERED), false)
                .map(Sort.Order::getProperty)
                .filter(property -> !properties.contains(property))
                .collect(Collectors.toList());

        if (!invalidProperties.isEmpty()) {
            throw new InvalidPropertiesException(invalidProperties);
        }
    }
}
