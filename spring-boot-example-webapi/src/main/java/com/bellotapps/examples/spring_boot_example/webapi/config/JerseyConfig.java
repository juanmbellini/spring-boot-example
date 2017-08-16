package com.bellotapps.examples.spring_boot_example.webapi.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import javax.ws.rs.ext.Provider;
import java.util.Arrays;
import java.util.Collection;

/**
 * Main Jersey configuration.
 */
public class JerseyConfig extends ResourceConfig {


    /**
     * Constructor.
     * Used to configure Jersey.
     */
    public JerseyConfig() {

        // Register packages containing providers
        registerPackages("com.bellotapps.examples.spring_boot_example.webapi.controller",
                "com.bellotapps.examples.spring_boot_example.webapi.exception_mappers");

        // Register Jackson as a JSON Provider
        register(jacksonJaxbJsonProvider());
    }


    /**
     * Configures a {@link JacksonJaxbJsonProvider} enabling and disabling certain
     * {@link SerializationFeature}s and {@link DeserializationFeature}s on a new {@link ObjectMapper}.
     *
     * @return The configured {@link JacksonJaxbJsonProvider}.
     */
    private JacksonJaxbJsonProvider jacksonJaxbJsonProvider() {
        final ObjectMapper om = new ObjectMapper();
        om.disable(DeserializationFeature.ACCEPT_FLOAT_AS_INT);
        om.enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);

        // Allows enums be serialized/deserialized using toString method (instead of name)
        om.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        om.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);

        return new JacksonJaxbJsonProvider(om, JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS);
    }

    /**
     * Register packages containing providers in this {@link ResourceConfig}.
     *
     * @param packages The packages containing providers.
     */
    private void registerPackages(String... packages) {
        // Register packages of in app Providers
        final ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Provider.class));
        scanner.addIncludeFilter(new AnnotationTypeFilter(Component.class));

        Arrays.stream(packages)
                .map(scanner::findCandidateComponents).flatMap(Collection::stream)
                .map(beanDefinition ->
                        ClassUtils.resolveClassName(beanDefinition.getBeanClassName(), this.getClassLoader()))
                .forEach(this::register);
    }

}
