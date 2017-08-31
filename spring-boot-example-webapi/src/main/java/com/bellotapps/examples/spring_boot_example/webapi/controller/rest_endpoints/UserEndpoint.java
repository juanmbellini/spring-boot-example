package com.bellotapps.examples.spring_boot_example.webapi.controller.rest_endpoints;

import com.bellotapps.examples.spring_boot_example.error_handling.helpers.ValidationExceptionThrower;
import com.bellotapps.examples.spring_boot_example.services.UserService;
import com.bellotapps.examples.spring_boot_example.models.User;
import com.bellotapps.examples.spring_boot_example.webapi.controller.dtos.entities.StringValueDto;
import com.bellotapps.examples.spring_boot_example.webapi.controller.dtos.entities.UserDto;
import com.bellotapps.examples.spring_boot_example.webapi.support.annotations.Java8Time;
import com.bellotapps.examples.spring_boot_example.webapi.support.annotations.JerseyController;
import com.bellotapps.examples.spring_boot_example.webapi.support.annotations.PaginationParam;
import com.bellotapps.examples.spring_boot_example.webapi.support.data_transfer.DateTimeFormatters;
import com.bellotapps.examples.spring_boot_example.webapi.support.exceptions.IllegalParamValueException;
import com.bellotapps.examples.spring_boot_example.webapi.support.exceptions.MissingJsonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * API endpoint for {@link User}s management.
 */
@Path(UserEndpoint.USERS_ENDPOINT)
@Produces(MediaType.APPLICATION_JSON)
@JerseyController
public class UserEndpoint implements ValidationExceptionThrower {

    /**
     * Endpoint for {@link User} management.
     */
    public static final String USERS_ENDPOINT = "users";

    /**
     * The {@link Logger} object.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserEndpoint.class);

    @Context
    private UriInfo uriInfo;

    /**
     * The {@link UserService}.
     */
    private final UserService userService;


    @Autowired
    public UserEndpoint(UserService userService) {
        this.userService = userService;
    }


    // ================================================================
    // API Methods
    // ================================================================

    // ======================================
    // Basic User Operation
    // ======================================


    @GET
    public Response findMatching(@QueryParam("fullName") final String fullName,
                                 @SuppressWarnings("RestParamTypeInspection")
                                 @QueryParam("minBirthDate")
                                 @Java8Time(formatter = DateTimeFormatters.ISO_LOCAL_DATE) final LocalDate minBirthDate,
                                 @SuppressWarnings("RestParamTypeInspection")
                                 @QueryParam("maxBirthDate")
                                 @Java8Time(formatter = DateTimeFormatters.ISO_LOCAL_DATE) final LocalDate maxBirthDate,
                                 @QueryParam("username") final String username,
                                 @QueryParam("email") final String email,
                                 @PaginationParam final Pageable pageable) {
        LOGGER.debug("Getting users matching");

        final Page<User> users = userService
                .findMatching(fullName, minBirthDate, maxBirthDate, username, email, pageable);
        return Response.ok(users.getContent().stream()
                .map(user -> new UserDto(user, getLocationUri(user, uriInfo)))
                .collect(Collectors.toList()))
                .build();
    }

    @GET
    @Path("{id : \\d+}")
    public Response getUserById(@PathParam("id") final long id) {
        LOGGER.debug("Getting user by id {}", id);

        return getUserBySomePropertyResponse(userService.getById(id), uriInfo);
    }

    @GET
    @Path("username/{username : .+}")
    public Response getUserByUsername(@PathParam("username") final String username) {
        LOGGER.debug("Getting user by username {}", username);

        return getUserBySomePropertyResponse(userService.getByUsername(username), uriInfo);
    }

    @GET
    @Path("email/{email : .+}")
    public Response getUserByEmail(@PathParam("email") final String email) {
        LOGGER.debug("Getting user by email {}", email);

        return getUserBySomePropertyResponse(userService.getByEmail(email), uriInfo);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(final UserDto userDto) {
        return Optional.ofNullable(userDto)
                .map(dto -> userService.register(userDto.getFullName(), userDto.getBirthDate(),
                        userDto.getUsername(), userDto.getEmail(), userDto.getPassword()))
                .map(user -> Response
                        .created(uriInfo.getAbsolutePathBuilder().path(String.valueOf(user.getId())).build())
                        .build())
                .orElseThrow(MissingJsonException::new);
    }

    @PUT
    @Path("{id: \\d+}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") final long id, final UserDto userDto) {
        if (id <= 0) {
            throw new IllegalParamValueException(Collections.singletonList("id"));
        }
        return Optional.ofNullable(userDto)
                .map(dto -> {
                    userService.update(id, userDto.getFullName(), userDto.getBirthDate());
                    return Response.noContent().build();
                })
                .orElseThrow(MissingJsonException::new);
    }


    @PUT
    @Path("{id: \\d+}/username")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changeUsername(@PathParam("id") final long id, final StringValueDto newUsernameDto) {
        if (id <= 0) {
            throw new IllegalParamValueException(Collections.singletonList("id"));
        }
        return Optional.ofNullable(newUsernameDto)
                .map(dto -> {
                    userService.changeUsername(id, dto.getValue());
                    return Response.noContent().build();
                })
                .orElseThrow(MissingJsonException::new);
    }

    @PUT
    @Path("{id: \\d+}/email")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changeEmail(@PathParam("id") final long id, final StringValueDto newEmailDto) {
        if (id <= 0) {
            throw new IllegalParamValueException(Collections.singletonList("id"));
        }
        return Optional.ofNullable(newEmailDto)
                .map(dto -> {
                    userService.changeEmail(id, dto.getValue());
                    return Response.noContent().build();
                })
                .orElseThrow(MissingJsonException::new);
    }

    @PUT
    @Path("{id: \\d+}/password")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changePassword(@PathParam("id") final long id, final UserDto.PasswordChangeDto passwordChangeDto) {
        if (id <= 0) {
            throw new IllegalParamValueException(Collections.singletonList("id"));
        }
        return Optional.ofNullable(passwordChangeDto)
                .map(dto -> {
                    userService.changePassword(id, dto.getCurrentPassword(), dto.getNewPassword());
                    return Response.noContent().build();
                })
                .orElseThrow(MissingJsonException::new);
    }

    @DELETE
    @Path("{id: \\d+}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("id") final long id) {
        if (id <= 0) {
            throw new IllegalParamValueException(Collections.singletonList("id"));
        }
        userService.deleteById(id);
        return Response.noContent().build();
    }

    @DELETE
    @Path("username/{username : .+}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteByUsername(@PathParam("username") final String username) {
        userService.deleteByUsername(username);
        return Response.noContent().build();
    }

    @DELETE
    @Path("email/{email : .+}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteByEmail(@PathParam("email") final String email) {
        userService.deleteByEmail(email);
        return Response.noContent().build();
    }


    // ======================================
    // Helper Methods
    // ======================================

    /**
     * Returns a {@link Response} according to the given {@code userOptional} content.
     *
     * @param userOptional The {@link Optional} that might hold a {@link User}.
     * @return A {@link Response} containing the {@link UserDto}
     * created with the held in the given {@link Optional} if present,
     * or a {@link Response.Status#NOT_FOUND} {@link Response} otherwise.
     */
    private static Response getUserBySomePropertyResponse(@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
                                                                  Optional<User> userOptional, UriInfo uriInfo) {
        return userOptional.map(user -> Response.ok(new UserDto(user, getLocationUri(user, uriInfo))))
                .orElse(Response.status(Response.Status.NOT_FOUND).entity(""))
                .build();
    }

    /**
     * Returns the location {@link URI} of the given {@link User}
     * according to the context hold by the given {@link UriInfo}
     *
     * @param user    The {@link User}'s whose location {@link URI} must be retrieved.
     * @param uriInfo The {@link UriInfo} holding the context.
     * @return The location {@link URI} of the given {@link User}
     */
    private static URI getLocationUri(User user, UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().clone()
                .path(USERS_ENDPOINT)
                .path(Long.toString(user.getId()))
                .build();
    }
}
