package com.bellotapps.examples.spring_boot_example.web.controller.dtos.api_errors;

import com.bellotapps.examples.spring_boot_example.error_handling.errros.UniqueViolationError;

import java.util.List;

/**
 * Data transfer object for client errors caused by trying to set a value that is already used and must be unique.
 */
public final class UniqueViolationErrorDto extends EntityErrorDto<UniqueViolationError> {

    /**
     * @param errors The {@link List} of {@link UniqueViolationError}s.
     */
    public UniqueViolationErrorDto(List<UniqueViolationError> errors) {
        super(ErrorFamily.UNIQUE_VIOLATION, errors);
    }
}