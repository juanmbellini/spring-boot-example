package com.bellotapps.examples.spring_boot_example.webapi.controller.exception_mappers;

import com.bellotapps.examples.spring_boot_example.exceptions.NoSuchEntityException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * The {@link ExceptionMapper} for {@link NoSuchEntityException}.
 * Will map into a <b>404 Not Found</b> response.
 */
@Provider
public class NoSucEntityExceptionMapper implements ExceptionMapper<NoSuchEntityException> {

    @Override
    public Response toResponse(NoSuchEntityException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity("") // Do not include nothing in the body
                .build();
    }
}