package com.bellotapps.examples.spring_boot_example.webapi.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Main Jersey configuration.
 */
public class JerseyConfig extends ResourceConfig {


    /**
     * Constructor.
     * Used to configure Jersey.
     */
    public JerseyConfig() {
        packages("com.bellotapps.examples.spring_boot_example.webapi.controller");
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
}
