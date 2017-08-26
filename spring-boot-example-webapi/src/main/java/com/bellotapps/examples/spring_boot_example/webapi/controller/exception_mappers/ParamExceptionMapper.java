package com.bellotapps.examples.spring_boot_example.webapi.controller.exception_mappers;

import com.bellotapps.examples.spring_boot_example.webapi.controller.dtos.api_errors.InvalidParamValueErrorDto;
import org.glassfish.jersey.server.ParamException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * The {@link ExceptionMapper} for {@link ParamException}.
 * Will map into a <b>400 Bad Request</b> response.
 */
@Provider
public class ParamExceptionMapper implements ExceptionMapper<ParamException> {

    @Override
    public Response toResponse(ParamException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new InvalidParamValueErrorDto(exception.getParameterName()))
                .build();
    }
}