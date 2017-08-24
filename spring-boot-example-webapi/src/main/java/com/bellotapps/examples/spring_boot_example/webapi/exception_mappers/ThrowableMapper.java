package com.bellotapps.examples.spring_boot_example.webapi.exception_mappers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by Juan Marcos Bellini on 24/8/17.
 * Questions at jbellini@bellotsapps.com
 */
@Provider
public class ThrowableMapper implements ExceptionMapper<Throwable> {

    /**
     * The {@link Logger} object.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ThrowableMapper.class);

    @Override
    public Response toResponse(Throwable exception) {
        LOGGER.error("An uncaught exception was thrown", exception);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("") // Do not include nothing in the body
                .build();
    }
}
