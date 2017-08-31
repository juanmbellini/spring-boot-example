package com.bellotapps.examples.spring_boot_example.web.controller.exception_mappers;

import com.bellotapps.examples.spring_boot_example.web.controller.dtos.api_errors.ServerErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * The {@link ExceptionMapper} for {@link TransactionException}.
 * Will map into a <b>503 Service Unavailable</b> response.
 */
@Provider
public class TransactionExceptionMapper implements ExceptionMapper<TransactionException> {

    /**
     * The {@link Logger} object.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionExceptionMapper.class);

    @Override
    public Response toResponse(TransactionException exception) {
        LOGGER.error("Could not access database", exception);

        return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                .entity(ServerErrorDto.DATABASE_ACCESS_ERROR_DTO)
                .build();
    }
}