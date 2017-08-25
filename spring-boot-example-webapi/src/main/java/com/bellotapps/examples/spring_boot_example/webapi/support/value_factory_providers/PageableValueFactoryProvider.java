package com.bellotapps.examples.spring_boot_example.webapi.support.value_factory_providers;

import com.bellotapps.examples.spring_boot_example.webapi.support.annotation.PaginationParam;
import com.bellotapps.examples.spring_boot_example.webapi.support.constants.DefaultValues;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.internal.inject.AbstractContainerRequestValueFactory;
import org.glassfish.jersey.server.model.Parameter;
import org.glassfish.jersey.server.spi.internal.ValueFactoryProvider;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A {@link ValueFactoryProvider} that creates a {@link PageableValueFactory},
 * which creates a {@link Pageable} from "page", "size", and "sort" query params.
 */
@Provider
public class PageableValueFactoryProvider implements ValueFactoryProvider {

    /**
     * The {@link ServiceLocator} which is used to inject values from query params.
     */
    private final ServiceLocator serviceLocator;

    /**
     * Constructor.
     *
     * @param serviceLocator The {@link ServiceLocator} which is used to inject values from query params.
     */
    @Inject
    public PageableValueFactoryProvider(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
    }

    @Override
    public Factory<?> getValueFactory(Parameter parameter) {
        // This ValueFactoryProvider only provides a ValueFactory for Pageable annotated with PaginationParams
        if (parameter.getRawType() == Pageable.class && parameter.isAnnotationPresent(PaginationParam.class)) {
            return new PageableValueFactory(serviceLocator);
        }
        return null;
    }

    @Override
    public PriorityType getPriority() {
        return Priority.NORMAL;
    }

    /**
     * The {@link Factory} that creates a {@link Pageable} from query params.
     */
    private static final class PageableValueFactory extends AbstractContainerRequestValueFactory<Pageable> {

        /**
         * The {@link ServiceLocator} which is used to inject values from query params.
         */
        private final ServiceLocator serviceLocator;

        /**
         * Constructor.
         *
         * @param serviceLocator The {@link ServiceLocator} which is used to inject values from query params.
         */
        private PageableValueFactory(ServiceLocator serviceLocator) {
            this.serviceLocator = serviceLocator;
        }


        @Override
        public Pageable provide() {
            final ParamsContainer container = new ParamsContainer();
            this.serviceLocator.inject(container); // Injects query params into the container object

            final List<Sort.Order> orders = container.sort.stream()
                    .map(str -> str.split(",", -1))
                    .map(propertyArray -> {
                        if (propertyArray.length == 0 || propertyArray.length > 2) {
                            throw new RuntimeException("Bad request (more than one ',')"); // TODO: Define exception
                        }
                        final String property = propertyArray[0];
                        if (property == null || !StringUtils.hasText(property)) {
                            throw new RuntimeException("Bad request (null or empty property)"); // TODO: Define exception
                        }
                        if (propertyArray.length == 1) {
                            return new Sort.Order(property);
                        }
                        try {
                            final Sort.Direction direction = Sort.Direction.fromString(propertyArray[1]);
                            return new Sort.Order(direction, property);
                        } catch (IllegalArgumentException e) {
                            throw new RuntimeException("Bad request (asc or desc)"); // TODO: Define exception
                        }
                    })
                    .collect(Collectors.toList());
            return new PageRequest(container.page, container.size, orders.isEmpty() ? null : new Sort(orders));
        }

        /**
         * A container class that holds the values taken from the query params.
         */
        private static final class ParamsContainer {

            @QueryParam("page")
            @DefaultValue(DefaultValues.DEFAULT_PAGE_NUMBER_STRING)
            @SuppressWarnings("unused")
            private Integer page;

            @QueryParam("size")
            @DefaultValue(DefaultValues.DEFAULT_PAGE_SIZE_STRING)
            @SuppressWarnings("unused")
            private Integer size;

            @QueryParam("sort")
            @SuppressWarnings({"unused", "MismatchedQueryAndUpdateOfCollection"})
            private List<String> sort;
        }
    }
}