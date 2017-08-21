package com.bellotapps.examples.spring_boot_example.persistence;

import com.bellotapps.examples.spring_boot_example.interfaces.daos.Filter;
import com.bellotapps.examples.spring_boot_example.interfaces.daos.FilterableJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

/**
 * Class implementing all custom repositories interfaces.
 */
@NoRepositoryBean
public class ExtendedJpaRepository<T, ID extends Serializable>
        extends SimpleJpaRepository<T, ID>
        implements FilterableJpaRepository<T, ID> {

    /**
     * The entity manager to perform entity operations.
     */
    private final EntityManager em;


    /**
     * Constructor.
     *
     * @param entityInformation The {@link JpaEntityInformation} needed by super class {@link SimpleJpaRepository}.
     * @param em                The entity manager to perform entity operations.
     */
    public ExtendedJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager em) {
        super(entityInformation, em);
        this.em = em;
    }


    @Override
    public Page<T> findAllMatching(Filter filter, Pageable pageable) {
        throw new UnsupportedOperationException(); // TODO: implement method
    }

    @Override
    public List<T> findAllMatching(Filter filter, Sort sort) {
        throw new UnsupportedOperationException(); // TODO: implement method
    }
}
