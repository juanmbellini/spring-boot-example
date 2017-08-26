package com.bellotapps.examples.spring_boot_example.webapi.controller.exception_mappers;

import com.bellotapps.examples.spring_boot_example.webapi.controller.dtos.api_errors.ServerErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * The {@link ExceptionMapper} for {@link Throwable}.
 * Will map into a <b>500 Internal Server Error</b> response.
 */
@Provider
public class ThrowableMapper implements ExceptionMapper<Throwable> {

    /**
     * The {@link Logger} object.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ThrowableMapper.class);

    @Override
    public Response toResponse(Throwable exception) {
        LOGGER.error("An uncaught exception was thrown", exception);

        return Response.serverError()
                .entity(new ServerErrorDto("There was an unexpected error"))
                .build();
    }
}
