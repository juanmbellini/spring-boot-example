package com.bellotapps.examples.spring_boot_example.web.error_handlers;

import com.bellotapps.examples.spring_boot_example.exceptions.UnauthenticatedException;
import com.bellotapps.utils.error_handler.ErrorHandler;
import com.bellotapps.utils.error_handler.ExceptionHandler;
import com.bellotapps.utils.error_handler.ExceptionHandlerObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;

/**
 * {@link ExceptionHandler} in charge of handling {@link UnauthenticatedException}.
 * Will result into a <b>500 Internal Server Error</b> response.
 */
@ExceptionHandlerObject
/* package */ class UnauthenticatedExceptionHandler implements ExceptionHandler<UnauthenticatedException> {

    /**
     * The {@link Logger} object.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UnauthenticatedExceptionHandler.class);

    @Override
    public ErrorHandler.HandlingResult handle(UnauthenticatedException exception) {
        LOGGER.debug("A user was not authenticated", exception);

        return new ErrorHandler.HandlingResult(Response.Status.UNAUTHORIZED.getStatusCode(), "");
    }
}
