package com.bellotapps.examples.spring_boot_example.web.security.authentication;

import com.bellotapps.examples.spring_boot_example.security.CurrentUserIdProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Concrete implementation of {@link CurrentUserIdProvider}.
 * Uses the {@link SecurityContext} to provide
 * the current {@link com.bellotapps.examples.spring_boot_example.models.User} id.
 */
@Component
/* package */ class CurrentUserIdProviderImpl implements CurrentUserIdProvider {

    /**
     * The {@link Logger} object.
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(CurrentUserIdProviderImpl.class);

    @Override
    public Optional<Long> currentUserIdOptional() {
        return getSecurityContext()
                .map(SecurityContext::getAuthentication)
                .map(authentication -> authentication instanceof JwtAuthenticationToken ?
                        (JwtAuthenticationToken) authentication : null)
                .map(JwtAuthenticationToken::getUserId);
    }

    /**
     * @return A nullable {@link Optional} containing the actual {@link SecurityContext}.
     */
    private Optional<SecurityContext> getSecurityContext() {
        try {
            return Optional.ofNullable(SecurityContextHolder.getContext());
        } catch (Throwable e) {
            LOGGER.debug("Security context could not be extracted", e);
            return Optional.empty();
        }
    }

}
