package com.bellotapps.examples.spring_boot_example.webapi.controller.dtos.api_errors;

/**
 * Data transfer object for API errors that are caused by the server.
 */
public class ServerErrorDto extends ApiErrorDto {

    /**
     * Constructor.
     *
     * @param message A human readable message for the person consuming the API.
     */
    public ServerErrorDto(String message) {
        super(ErrorKind.SERVER, message);
    }

    /**
     * A {@link ServerErrorDto} to be sent when not being able to access the database.
     */
    public static final ServerErrorDto DATABASE_ACCESS_ERROR_DTO =
            new ServerErrorDto("The service is currently unavailable");
}
