package com.bellotapps.examples.spring_boot_example.webapi.controller.exception_mappers;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * The {@link ExceptionMapper} for {@link WebApplicationException}.
 * Will map into a the response got from {@link WebApplicationException#getResponse()}, with empty entity.
 */
@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Override
    public Response toResponse(WebApplicationException exception) {
        return Response.fromResponse(exception.getResponse())
                .entity("")
                .build();
    }
}