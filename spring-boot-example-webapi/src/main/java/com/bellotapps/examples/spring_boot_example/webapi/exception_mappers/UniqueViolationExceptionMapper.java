package com.bellotapps.examples.spring_boot_example.webapi.exception_mappers;

import com.bellotapps.examples.spring_boot_example.exceptions.UniqueViolationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * The {@link ExceptionMapper} for {@link UniqueViolationException}.
 * Will map into a <b>403 Forbidden</b> response.
 */
@Provider
public class UniqueViolationExceptionMapper implements ExceptionMapper<UniqueViolationException> {

    @Override
    public Response toResponse(UniqueViolationException exception) {
        return Response.status(Response.Status.CONFLICT)
                .entity(exception.getErrors())
                .build();
    }
}
