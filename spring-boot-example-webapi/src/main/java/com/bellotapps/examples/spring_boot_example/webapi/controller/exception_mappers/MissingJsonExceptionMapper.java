package com.bellotapps.examples.spring_boot_example.webapi.controller.exception_mappers;

import com.bellotapps.examples.spring_boot_example.webapi.support.exceptions.MissingJsonException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * The {@link ExceptionMapper} for {@link MissingJsonException}.
 * Will into the response got from {@link JsonProcessingExceptionMapper#jsonProcessingExceptionResponse()}.
 */
@Provider
public class MissingJsonExceptionMapper implements ExceptionMapper<MissingJsonException> {

    @Override
    public Response toResponse(MissingJsonException exception) {
        return JsonProcessingExceptionMapper.jsonProcessingExceptionResponse();
    }
}
