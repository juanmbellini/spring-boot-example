package com.bellotapps.examples.spring_boot_example.interfaces.daos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Created by Juan Marcos Bellini on 21/8/17.
 * Questions at jbellini@bellotsapps.com
 */
@NoRepositoryBean
public interface FilterableJpaRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    /**
     * Returns a {@link Page} of entities of type {@code T} applying the given {@link Filter},
     * and sorted by the given options.
     *
     * @param filter   The {@link Filter} to be applied to the query.
     * @param pageable The {@link Pageable} with the information to create the {@link Page} to be returned.
     * @return The matching {@link Page}.
     */
    Page<T> findAllMatching(Filter filter, Pageable pageable);

    /**
     * Returns all entities applying the given {@link Filter}, and sorted by the given options.
     *
     * @param sort The sorting options.
     * @return The entities that match the given {@link Filter}, sorted by the given options
     */
    List<T> findAllMatching(Filter filter, Sort sort);

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@code null}.
     * @return A <b>nullable</b> {@link Optional} of the specific type {@code T}
     * containing the entity with the given id, or {@code null} if none found
     * @throws IllegalArgumentException If {@code id} is {@code null}.
     */
    default Optional<T> findById(ID id) {
        return Optional.ofNullable(this.findOne(id));
    }


}
