package com.bellotapps.examples.spring_boot_example.web.controller.rest_endpoints;

import com.bellotapps.examples.spring_boot_example.models.User;
import com.bellotapps.examples.spring_boot_example.services.AuthenticationService;
import com.bellotapps.examples.spring_boot_example.web.controller.dtos.authentication.LoginCredentialsDto;
import com.bellotapps.examples.spring_boot_example.web.support.annotations.JerseyController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * API endpoint for sessions management.
 */
@Path(SessionEndpoint.SESSIONS_ENDPOINT)
@Produces(MediaType.APPLICATION_JSON)
@JerseyController
public class SessionEndpoint {

    /**
     * Endpoint for {@link User} management.
     */
    public static final String SESSIONS_ENDPOINT = "/auth";

    /**
     * The {@link Logger} object.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserEndpoint.class);

    /**
     * Service in charge of performing authentication.
     */
    private final AuthenticationService authenticationService;

    @Autowired
    public SessionEndpoint(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @POST
    @Path("/login")
    public Response login(LoginCredentialsDto loginCredentialsDto) {
        LOGGER.debug("Trying to log in user with username {}", loginCredentialsDto.getUsername());

        // Create a JWT (i.e this creates a "session")
        final AuthenticationService.UserAndTokenContainer container = authenticationService
                .login(loginCredentialsDto.getUsername(), loginCredentialsDto.getPassword());

        LOGGER.debug("User {} successfully logged in", container.getUser().getUsername());

        return Response.noContent()
                .header("X-Token", container.getToken())
                .build();
    }
}
