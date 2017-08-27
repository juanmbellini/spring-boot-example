package com.bellotapps.examples.spring_boot_example.webapi.controller.exception_mappers;

import com.bellotapps.examples.spring_boot_example.webapi.controller.dtos.api_errors.ServerErrorDto;
import org.hibernate.JDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * The {@link ExceptionMapper} for {@link JDBCException}.
 * Will map into a <b>503 Service Unavailable</b> response.
 */
@Provider
public class JDBCExceptionMapper implements ExceptionMapper<JDBCException> {

    /**
     * The {@link Logger} object.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JDBCExceptionMapper.class);

    @Override
    public Response toResponse(JDBCException exception) {
        LOGGER.error("An uncaught exception was thrown", exception);

        return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                .entity(new ServerErrorDto("The service is currently unavailable"))
                .build();
    }
}
