package com.bellotapps.examples.spring_boot_example.webapi.config;

import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


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
     * Jersey Servlet Container Bean configuration
     *
     * @return A {@link ServletRegistrationBean} configured with Jersey's {@link ServletContainer}.
     */
    @Bean
    public ServletRegistrationBean jerseyServlet() {

        final ServletRegistrationBean servletBean = new ServletRegistrationBean(new ServletContainer(), "/*");
        servletBean.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyConfig.class.getName());
        servletBean.setLoadOnStartup(1);
        return servletBean;
    }
}
