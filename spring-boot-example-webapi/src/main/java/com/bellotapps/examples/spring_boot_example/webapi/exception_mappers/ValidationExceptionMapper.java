package com.bellotapps.examples.spring_boot_example.webapi.exception_mappers;

import com.bellotapps.examples.spring_boot_example.exceptions.ValidationException;
import com.bellotapps.examples.spring_boot_example.webapi.support.constants.MissingHttpStatuses;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * The {@link ExceptionMapper} for {@link ValidationException}.
 * Will map into a <b>422 Unprocessable Entity</b> response.
 */
@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

    @Override
    public Response toResponse(ValidationException exception) {
        return Response.status(MissingHttpStatuses.UNPROCESSABLE_ENTITY)
                .entity(exception.getErrors())
                .build();
    }
}
