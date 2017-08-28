package com.bellotapps.examples.spring_boot_example.webapi.controller.exception_mappers;

import com.bellotapps.examples.spring_boot_example.exceptions.InvalidPropertiesException;
import com.bellotapps.examples.spring_boot_example.webapi.controller.dtos.api_errors.IllegalParamValueErrorDto;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * The {@link ExceptionMapper} for {@link InvalidPropertiesException}.
 * Will into the response got from
 * {@link IllegalParamValueExceptionMapper#illegalParamValueResponse(IllegalParamValueErrorDto)}
 */
@Provider
public class InvalidPropertyExceptionMapper implements ExceptionMapper<InvalidPropertiesException> {

    @Override
    public Response toResponse(InvalidPropertiesException exception) {
        return IllegalParamValueExceptionMapper
                .illegalParamValueResponse(new IllegalParamValueErrorDto(exception.getErrors()));
    }
}
