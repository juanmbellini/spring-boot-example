package com.bellotapps.examples.spring_boot_example.application;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * Class that provides Spring Beans for app configuration.
 */
@Configuration
@ComponentScan(value = {
        "com.bellotapps.examples.spring_boot_example.web.config",
        "com.bellotapps.examples.spring_boot_example.services",
        "com.bellotapps.examples.spring_boot_example.persistence",
})
public class AppConfig {

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
}
