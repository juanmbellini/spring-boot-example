package com.bellotapps.examples.spring_boot_example.web.support.annotations;

import com.bellotapps.examples.spring_boot_example.application.AppConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ext.Provider;
import java.lang.annotation.*;

/**
 * Indicates that a class is a Jersey Controller.
 * <p>
 * <p>This annotation serves as a specialization of {@link Component},
 * allowing for implementation classes to be autodetected through classpath scanning,
 * and {@link Provider}, allowing annotated classes to be detected for registering by
 * {@link AppConfig#registerPackages(ResourceConfig, String...)}.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Provider
@Component
public @interface JerseyController {
}
