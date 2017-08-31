package com.bellotapps.examples.spring_boot_example.web.controller.exception_mappers;

import com.bellotapps.examples.spring_boot_example.web.controller.dtos.api_errors.RepresentationErrorDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * The {@link ExceptionMapper} for {@link JsonProcessingException}.
 * Will map into a <b>400 Bad Request</b> response.
 */
@Provider
public class JsonProcessingExceptionMapper implements ExceptionMapper<JsonProcessingException> {

    @Override
    public Response toResponse(JsonProcessingException exception) {
        return jsonProcessingExceptionResponse();
    }

    /**
     * Static method that creates a <b>400 Bad Request</b> response
     * including the {@link RepresentationErrorDto#REPRESENTATION_ERROR_DTO} as the response body.
     * Can be reused by other {@link ExceptionMapper}s that must return
     * the {@link {@link RepresentationErrorDto#REPRESENTATION_ERROR_DTO}} in a <b>400 Bad Request</b> response.
     *
     * @return The {@link Response}.
     */
    /* package */
    static Response jsonProcessingExceptionResponse() {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(RepresentationErrorDto.REPRESENTATION_ERROR_DTO)
                .build();
    }
}