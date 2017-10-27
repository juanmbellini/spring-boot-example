package com.bellotapps.examples.spring_boot_example.web.controller.rest_endpoints;

import com.bellotapps.examples.spring_boot_example.services.LoginService;
import com.bellotapps.examples.spring_boot_example.services.SessionService;
import com.bellotapps.examples.spring_boot_example.web.controller.dtos.authentication.LoginCredentialsDto;
import com.bellotapps.examples.spring_boot_example.web.support.annotations.Base64url;
import com.bellotapps.examples.spring_boot_example.web.support.annotations.JerseyController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * API endpoint for sessions management.
 */
@Path(SessionEndpoint.SESSIONS_ENDPOINT)
@Produces(MediaType.APPLICATION_JSON)
@JerseyController
public class SessionEndpoint {

    /**
     * Endpoint for session management.
     */
    public static final String SESSIONS_ENDPOINT = "/auth";

    /**
     * Endpoint for login mechanism.
     */
    public static final String LOGIN_ENDPOINT = "/login";

    /**
     * Endpoint for logout mechanism.
     */
    public static final String LOGOUT_ROOT_ENDPOINT = "/logout";


    /**
     * The {@link Logger} object.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserEndpoint.class);

    /**
     * Service in charge of performing password authentication.
     */
    private final LoginService loginService;

    /**
     * Service in charge of managing sessions.
     */
    private final SessionService sessionService;

    @Context
    private UriInfo uriInfo;

    @Autowired
    public SessionEndpoint(LoginService loginService, SessionService sessionService) {
        this.loginService = loginService;
        this.sessionService = sessionService;
    }


    @POST
    @Path(LOGIN_ENDPOINT)
    public Response login(LoginCredentialsDto loginCredentialsDto) {
        LOGGER.debug("Trying to log in user with username {}", loginCredentialsDto.getUsername());

        // Create a JWT (i.e this creates a "session")
        final LoginService.UserTokenAndJtiContainer container = loginService
                .login(loginCredentialsDto.getUsername(), loginCredentialsDto.getPassword());

        LOGGER.debug("User {} successfully logged in", container.getUser().getUsername());

        // Generate url to perform logout of the new generated session
        final String logoutUrl = uriInfo.getBaseUriBuilder()
                .path(SESSIONS_ENDPOINT)
                .path(LOGOUT_ROOT_ENDPOINT)
                .path(Long.toString(container.getUser().getId()))
                .path(Base64Utils.encodeToUrlSafeString(Long.toString(container.getJti()).getBytes()))
                .build()
                .toString();

        return Response.noContent()
                .header("X-Token", container.getToken())
                .header("X-Logout-Url", logoutUrl)
                .build();
    }

    @DELETE
    @Path(LOGOUT_ROOT_ENDPOINT + "/{userId : \\d+}/{jti: .+}")
    public Response logout(@SuppressWarnings("RSReferenceInspection") @PathParam("userId") final long userId,
                           @SuppressWarnings("RSReferenceInspection") @PathParam("jti") @Base64url final Long jti) {
        LOGGER.debug("Trying to log out user with id {}", userId);
        sessionService.invalidateSession(userId, jti);

        return Response.noContent().build();
    }
}
