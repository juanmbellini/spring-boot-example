package com.bellotapps.examples.spring_boot_example.webapi.exception_mappers;

import com.bellotapps.examples.spring_boot_example.exceptions.UnauthorizedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * The {@link ExceptionMapper} for {@link UnauthorizedException}.
 * Will map into a <b>403 Forbidden</b> response.
 */
@Provider
public class UnauthorizedExceptionMapper implements ExceptionMapper<UnauthorizedException> {

    @Override
    public Response toResponse(UnauthorizedException exception) {
        return Response.status(Response.Status.FORBIDDEN)
                .entity("") // Do not include nothing in the body
                .build();
    }
}
