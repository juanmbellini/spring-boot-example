package com.bellotapps.examples.spring_boot_example.persistence;

import com.bellotapps.examples.spring_boot_example.exceptions.InvalidPropertiesException;
import com.bellotapps.examples.spring_boot_example.models.Session;
import com.bellotapps.examples.spring_boot_example.persistence.query_helpers.SessionQueryHelper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * Concrete implementation of a {@link SessionQueryHelper}.
 */
@Component
public class SessionQueryHelperImpl implements SessionQueryHelper {

    @Override
    public void validatePageable(Pageable pageable) throws InvalidPropertiesException {
        PersistenceHelper.validatePageable(pageable, Session.class);
    }
}
