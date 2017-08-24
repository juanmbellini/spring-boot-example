package com.bellotapps.examples.spring_boot_example.webapi.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

import javax.ws.rs.ext.Provider;
import java.util.Arrays;
import java.util.Collection;


/**
 * Class that provides Spring Beans for app configuration.
 */
@Configuration
@ComponentScan({"com.bellotapps.examples.spring_boot_example.webapi.controller",
        "com.bellotapps.examples.spring_boot_example.services",
        "com.bellotapps.examples.spring_boot_example.persistence",
})
public class AppConfig {

    /**
     * Creates a {@link ResourceConfig} in order to configure Jersey's behaviour.
     *
     * @param jacksonJaxbJsonProvider The {@link Provider} used to serialize/deserialize JSON from and into POJOs.
     * @return The {@link ResourceConfig} used to configure Jersey's behaviour.
     */
    @Bean
    @Autowired
    public ResourceConfig jerseyConfig(JacksonJaxbJsonProvider jacksonJaxbJsonProvider) {
        final ResourceConfig jerseyConfig = new ResourceConfig();
        registerPackages(jerseyConfig,
                "com.bellotapps.examples.spring_boot_example.webapi.controller",
                "com.bellotapps.examples.spring_boot_example.webapi.exception_mappers");
        jerseyConfig.register(jacksonJaxbJsonProvider);

        return jerseyConfig;
    }

    /**
     * Configures a {@link JacksonJaxbJsonProvider} using the given {@link ObjectMapper}
     * and the {@link JacksonJaxbJsonProvider#DEFAULT_ANNOTATIONS}
     * on a new {@link ObjectMapper}.
     *
     * @param om The {@link ObjectMapper} used to map JSON from and into POJOs.
     * @return The configured {@link JacksonJaxbJsonProvider}.
     */
    @Autowired
    @Bean
    public JacksonJaxbJsonProvider jacksonJaxbJsonProvider(ObjectMapper om) {
        return new JacksonJaxbJsonProvider(om, JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS);
    }

    /**
     * Configures an {@link ObjectMapper} enabling and disabling certain
     * {@link SerializationFeature}s and {@link DeserializationFeature}s
     *
     * @return The configured {@link ObjectMapper}.
     */
    @Bean
    public ObjectMapper jacksonObjectMapper() {
        final ObjectMapper om = new ObjectMapper();
        // Serialization
        om.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        // Deserialization
        om.disable(DeserializationFeature.ACCEPT_FLOAT_AS_INT);
        om.enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
        om.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);

        return om;
    }


    // ================================
    // Helpers
    // ================================

    /**
     * Registers the classes annotated with the {@link Provider} annotation in the given {@code packages}.
     * This allows package scanning with Jersey (as currently not supported by library).
     *
     * @param packages The packages containing providers.
     */
    private static void registerPackages(ResourceConfig resourceConfig, String... packages) {
        // Register packages of in app Providers
        final ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Provider.class));

        Arrays.stream(packages)
                .map(scanner::findCandidateComponents).flatMap(Collection::stream)
                .map(beanDefinition ->
                        ClassUtils.resolveClassName(beanDefinition.getBeanClassName(), resourceConfig.getClassLoader()))
                .forEach(resourceConfig::register);
    }
}
