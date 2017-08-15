package com.bellotapps.examples.spring_boot_example.webapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.time.LocalTime;

/**
 * Example controller class
 */
@Component
@Path("/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Context
    private UriInfo uriInfo;

    @GET
    public Response getHelloWorld() {
        LOGGER.info("Hello, world. Time is {}", LocalTime.now());
        LOGGER.warn("Hello, world! This is a warning log! Time is {}", LocalTime.now());
        LOGGER.error("Hello, world! This is an error log!! Time is {}", LocalTime.now());
        return Response.ok("Hello world from Spring Boot with Jersey")
                .location(uriInfo.getAbsolutePath())
                .build();
    }

}
