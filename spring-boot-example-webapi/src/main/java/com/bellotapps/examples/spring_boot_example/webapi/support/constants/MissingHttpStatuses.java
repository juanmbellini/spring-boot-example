package com.bellotapps.examples.spring_boot_example.webapi.support.constants;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Enum containing missing {@link javax.ws.rs.core.Response.StatusType}
 * not included in {@link javax.ws.rs.core.Response.Status} enum.
 */
public enum MissingHttpStatuses implements Response.StatusType {
    UNPROCESSABLE_ENTITY(422, "Unprocessable Entity");

    /**
     * The associated status code.
     */
    private final int code;
    /**
     * The reason phrase.
     */
    private final String reason;
    /**
     * The class of status code.
     */
    private final Response.Status.Family family;


    /**
     * Constructor.
     *
     * @param statusCode   The associated status code.
     * @param reasonPhrase The reason phrase.
     */
    MissingHttpStatuses(final int statusCode, final String reasonPhrase) {
        this.code = statusCode;
        this.reason = reasonPhrase;
        this.family = Response.Status.Family.familyOf(statusCode);
    }

    /**
     * @return The class of status code.
     */
    @Override
    public Response.Status.Family getFamily() {
        return family;
    }

    /**
     * @return The associated status code.
     */
    @Override
    public int getStatusCode() {
        return code;
    }

    /**
     * @return The reason phrase.
     */
    @Override
    public String getReasonPhrase() {
        return this.toString();
    }

    /**
     * Get the reason phrase.
     *
     * @return the reason phrase.
     */
    @Override
    public String toString() {
        return reason;
    }

    /**
     * Convert a numerical status code into the corresponding Status.
     * Supports {@link javax.ws.rs.core.Response.StatusType} declared in this enum,
     * and also declared in {@link javax.ws.rs.core.Response.Status} enum.
     * (using {@link javax.ws.rs.core.Response.StatusType} instead of {@link javax.ws.rs.core.Response.Status}).
     *
     * @param statusCode The numerical status code.
     * @return The matching Status, or {@code null} is no matching Status is defined.
     */
    public static Response.StatusType fromStatusCode(final int statusCode) {
        for (Response.StatusType status : MissingHttpStatuses.values()) {
            if (status.getStatusCode() == statusCode) {
                return status;
            }
        }
        return Response.Status.fromStatusCode(statusCode);
    }
}
