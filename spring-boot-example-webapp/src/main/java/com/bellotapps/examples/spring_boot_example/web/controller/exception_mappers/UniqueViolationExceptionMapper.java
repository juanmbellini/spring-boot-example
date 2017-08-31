package com.bellotapps.examples.spring_boot_example.web.controller.exception_mappers;

import com.bellotapps.examples.spring_boot_example.exceptions.UniqueViolationException;
import com.bellotapps.examples.spring_boot_example.web.controller.dtos.api_errors.UniqueViolationErrorDto;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * The {@link ExceptionMapper} for {@link UniqueViolationException}.
 * Will map into a <b>409 Conflict</b> response.
 */
@Provider
public class UniqueViolationExceptionMapper implements ExceptionMapper<UniqueViolationException> {

    @Override
    public Response toResponse(UniqueViolationException exception) {
        return Response.status(Response.Status.CONFLICT)
                .entity(new UniqueViolationErrorDto(exception.getErrors()))
                .build();
    }
}