package com.bellotapps.examples.spring_boot_example.webapi.controller;

import com.bellotapps.examples.spring_boot_example.interfaces.services.UserService;
import com.bellotapps.examples.spring_boot_example.models.User;
import com.bellotapps.examples.spring_boot_example.webapi.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Example controller class
 */
@Component
@Path("/users")
@Produces(value = {MediaType.APPLICATION_JSON,})
public class UserController {

    /**
     * The {@link Logger} object.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Context
    private UriInfo uriInfo;

    /**
     * The {@link UserService}.
     */
    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GET
    public Response getAllUsers() {
        LOGGER.debug("Getting all users");

        final List<User> users = userService.getAll();
        return Response.ok(users.stream().map(UserDto::new).collect(Collectors.toList())).build();
    }

    @GET
    @Path("/{id : \\d+}")
    public Response getUserById(@PathParam("id") final long id) {
        LOGGER.debug("Getting user by id {}", id);

        return userService.getById(id)
                .map(user -> Response.ok(new UserDto(user)))
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }
}
