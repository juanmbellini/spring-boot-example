package com.bellotapps.examples.spring_boot_example.web.config;

import com.bellotapps.utils.error_handler.EnableErrorHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class in charge of configuring web concerns.
 */
@Configuration
@ComponentScan({
        "com.bellotapps.examples.spring_boot_example.web.controller",
})
@EnableErrorHandler(basePackages = "com.bellotapps.examples.spring_boot_example.web.error_handlers")
public class WebConfig {
}
