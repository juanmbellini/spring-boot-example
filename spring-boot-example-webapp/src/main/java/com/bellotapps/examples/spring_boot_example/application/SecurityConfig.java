package com.bellotapps.examples.spring_boot_example.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring Security main configuration.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.bellotapps.examples.spring_boot_example.security",
        "com.bellotapps.examples.spring_boot_example.web.security",
})
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
