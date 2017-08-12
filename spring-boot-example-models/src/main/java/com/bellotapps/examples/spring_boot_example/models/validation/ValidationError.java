package com.bellotapps.examples.spring_boot_example.models.validation;

/**
 * Created by Juan Marcos Bellini on 12/8/17.
 * Questions at jbellini@bellotsapps.com
 */
public class ValidationError {

    /**
     * The {@link ErrorCause} representing what caused the new error.
     */
    private final ErrorCause cause;

    /**
     * The fields that did not validate.
     */
    private final String field;

    /**
     * A helper message to be used for debugging purposes.
     */
    private final String message;

    /**
     * Constructor.
     *
     * @param cause   The {@link ErrorCause} representing what caused the new error.
     * @param field   The field that did not validate.
     * @param message A helper message to be used for debugging purposes.
     */
    public ValidationError(ErrorCause cause, String field, String message) {
        this.cause = cause;
        this.field = field;
        this.message = message;
    }


    /**
     * @return The {@link ErrorCause} representing what caused the new error.
     */
    public ErrorCause getCause() {
        return cause;
    }

    /**
     * @return The fields that did not validate.
     */
    public String getField() {
        return field;
    }

    /**
     * @return A helper message to be used for debugging purposes.
     */
    public String getMessage() {
        return message;
    }

    /**
     * En enum indicating types of validation errors.
     */
    public enum ErrorCause {
        /**
         * Error that occurs when a mandatory value is missing (i.e was set to {@code null}).
         */
        MISSING_VALUE,
        /**
         * Error that occurs when a value does not match the constraints established for it.
         */
        ILLEGAL_VALUE,
    }
}
