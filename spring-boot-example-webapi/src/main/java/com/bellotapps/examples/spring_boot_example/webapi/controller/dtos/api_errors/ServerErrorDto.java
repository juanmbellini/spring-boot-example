package com.bellotapps.examples.spring_boot_example.webapi.controller.dtos.api_errors;

/**
 * Data transfer object for API errors that are caused by the server.
 */
public abstract class ServerErrorDto extends ApiErrorDto {

    /**
     * Constructor.
     *
     * @param message A human readable message for the person consuming the API.
     */
    public ServerErrorDto(String message) {
        super(ErrorKind.SERVER, message);
    }
}
