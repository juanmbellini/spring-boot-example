package com.bellotapps.examples.spring_boot_example.persistence;

import org.hibernate.criterion.MatchMode;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import java.util.Objects;

/**
 * Class implementing helper methods for the persistence layer.
 */
/* package */ class PersistenceHelper {

    /**
     * Creates a {@link Predicate} representing a "like",
     * which matches the given {@code attributeName}
     * (of the {@link EntityType} got by executing {@link Root#getModel()} from the given {@link Root})
     * with the given {@code pattern}.
     *
     * @param cb            The {@link CriteriaBuilder} used to build the {@link Predicate}.
     * @param root          The {@link Root} from where the {@link Predicate} will be used.
     * @param attributeName The attribute name from the given {@link EntityType} to match
     * @param pattern       The pattern to match.
     * @param matchMode     The {@link MatchMode} to be applied (i.e exact, start, end or anywhere).
     * @param caseSensitive A flag indicating if the match must be case sensitive.
     * @param <T>           The concrete type of {@link EntityType} to which this predicate is applied.
     * @return The resulting {@link Predicate}.
     */
    /* package */
    static <T> Predicate toLikePredicate(CriteriaBuilder cb, Root<T> root, String attributeName, String pattern,
                                         MatchMode matchMode,
                                         @SuppressWarnings("SameParameterValue") boolean caseSensitive) {
        Objects.requireNonNull(cb, "The CriteriaBuilder must not be null");
        Objects.requireNonNull(root, "The Root must not be null");
        Objects.requireNonNull(attributeName, "The attribute name must not be null");
        Objects.requireNonNull(pattern, "The pattern must not be null");
        Objects.requireNonNull(matchMode, "The MatchMode must not be null");

        final Path<String> attributePath = root.get(root.getModel()
                .getDeclaredSingularAttribute(attributeName, String.class));
        return caseSensitive ?
                cb.like(attributePath, matchMode.toMatchString(pattern)) :
                cb.like(cb.lower(attributePath), matchMode.toMatchString(pattern.toLowerCase()));
    }
}
