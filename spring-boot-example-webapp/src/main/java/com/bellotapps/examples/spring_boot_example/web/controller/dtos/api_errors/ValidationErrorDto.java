package com.bellotapps.examples.spring_boot_example.web.controller.dtos.api_errors;

import com.bellotapps.examples.spring_boot_example.error_handling.errros.ValidationError;

import java.util.List;

/**
 * Data transfer object for client errors caused when an entity is not valid (there are missing or illegal values).
 */
public final class ValidationErrorDto extends EntityErrorDto<ValidationError> {

    /**
     * Constructor.
     *
     * @param errors The {@link List} of {@link ValidationError}s.
     */
    public ValidationErrorDto(List<ValidationError> errors) {
        super(ErrorFamily.VALIDATION, errors);
    }
}