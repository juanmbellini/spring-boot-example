package com.bellotapps.examples.spring_boot_example.web.error_handlers;

import com.bellotapps.examples.spring_boot_example.exceptions.UniqueViolationException;
import com.bellotapps.examples.spring_boot_example.web.controller.dtos.api_errors.UniqueViolationErrorDto;
import com.bellotapps.utils.error_handler.ErrorHandler;
import com.bellotapps.utils.error_handler.ExceptionHandler;
import com.bellotapps.utils.error_handler.ExceptionHandlerObject;

import javax.ws.rs.core.Response;

/**
 * {@link ExceptionHandler} in charge of handling {@link UniqueViolationException}.
 * Will result into a <b>409 Conflict</b> response.
 */
@ExceptionHandlerObject
/* package */ class UniqueViolationExceptionHandler implements ExceptionHandler<UniqueViolationException> {

    @Override
    public ErrorHandler.HandlingResult handle(UniqueViolationException exception) {
        return new ErrorHandler.HandlingResult(Response.Status.CONFLICT.getStatusCode(),
                new UniqueViolationErrorDto(exception.getErrors()));
    }
}
