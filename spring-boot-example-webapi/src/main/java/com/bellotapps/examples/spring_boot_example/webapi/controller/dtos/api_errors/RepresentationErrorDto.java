package com.bellotapps.examples.spring_boot_example.webapi.controller.dtos.api_errors;

/**
 * Data transfer object for client errors caused by a bad representation of an entity.
 */
public final class RepresentationErrorDto extends ClientErrorDto {

    /**
     * Constructor.
     */
    public RepresentationErrorDto() {
        super(ErrorFamily.REPRESENTATION);
    }
}