package com.bellotapps.examples.spring_boot_example.webapi.config;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Bootstrap class.
 */
@SpringBootApplication
public class Bootstrap {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Bootstrap.class)
                .bannerMode(Banner.Mode.OFF)
                .build().run(args);
    }
}
