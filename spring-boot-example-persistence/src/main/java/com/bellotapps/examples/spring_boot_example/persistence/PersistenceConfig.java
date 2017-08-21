package com.bellotapps.examples.spring_boot_example.persistence;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Configuration class to set up persistence layer.
 */
@Configuration
@EnableJpaRepositories(basePackages = {
        "com.bellotapps.examples.spring_boot_example.interfaces.daos",
}, repositoryBaseClass = ExtendedJpaRepository.class)
@EntityScan("com.bellotapps.examples.spring_boot_example.models")
public class PersistenceConfig {
}
