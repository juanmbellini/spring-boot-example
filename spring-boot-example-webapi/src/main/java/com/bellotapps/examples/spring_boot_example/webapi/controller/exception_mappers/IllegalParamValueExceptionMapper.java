package com.bellotapps.examples.spring_boot_example.webapi.controller.exception_mappers;

import com.bellotapps.examples.spring_boot_example.webapi.controller.dtos.api_errors.IllegalParamValueErrorDto;
import com.bellotapps.examples.spring_boot_example.webapi.support.exceptions.IllegalParamValueException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * The {@link ExceptionMapper} for {@link IllegalParamValueException}.
 * Will map into a <b>400 Bad Request</b> response.
 */
@Provider
public class IllegalParamValueExceptionMapper implements ExceptionMapper<IllegalParamValueException> {

    @Override
    public Response toResponse(IllegalParamValueException exception) {
        return illegalParamValueResponse(new IllegalParamValueErrorDto(exception.getConflictingParams()));
    }

    /**
     * Static method that creates a <b>400 Bad Request</b> response
     * including the given {@link IllegalParamValueErrorDto} as the response body.
     * Can be reused by other {@link ExceptionMapper}s that must return an {@link IllegalParamValueErrorDto}
     * in a <b>400 Bad Request</b> response.
     *
     * @param illegalParamValueErrorDto The {@link IllegalParamValueErrorDto} to be included in the body.
     * @return The {@link Response}.
     */
    /* package */
    static Response illegalParamValueResponse(IllegalParamValueErrorDto illegalParamValueErrorDto) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(illegalParamValueErrorDto)
                .build();
    }
}