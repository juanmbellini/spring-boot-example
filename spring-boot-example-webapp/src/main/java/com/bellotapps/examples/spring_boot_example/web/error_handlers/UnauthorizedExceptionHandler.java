package com.bellotapps.examples.spring_boot_example.web.error_handlers;

import com.bellotapps.examples.spring_boot_example.exceptions.UnauthorizedException;
import com.bellotapps.utils.error_handler.ErrorHandler;
import com.bellotapps.utils.error_handler.ExceptionHandler;
import com.bellotapps.utils.error_handler.ExceptionHandlerObject;

import javax.ws.rs.core.Response;

/**
 * {@link ExceptionHandler} in charge of handling {@link UnauthorizedException}.
 * Will result into a <b>403 Forbidden</b> response.
 */
@ExceptionHandlerObject
/* package */ class UnauthorizedExceptionHandler implements ExceptionHandler<UnauthorizedException> {

    @Override
    public ErrorHandler.HandlingResult handle(UnauthorizedException exception) {
        return new ErrorHandler.HandlingResult(Response.Status.FORBIDDEN.getStatusCode(), null);
    }
}
