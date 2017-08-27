package com.bellotapps.examples.spring_boot_example.webapi.controller.exception_mappers;

import com.bellotapps.examples.spring_boot_example.webapi.controller.dtos.api_errors.ServerErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * The {@link ExceptionMapper} for {@link DataAccessException}.
 * Will map into a <b>503 Service Unavailable</b> response.
 */
@Provider
public class DataAccessExceptionMapper implements ExceptionMapper<DataAccessException> {

    /**
     * The {@link Logger} object.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DataAccessExceptionMapper.class);

    @Override
    public Response toResponse(DataAccessException exception) {
        LOGGER.error("An uncaught exception was thrown", exception);

        return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                .entity(new ServerErrorDto("The service is currently unavailable"))
                .build();
    }
}
