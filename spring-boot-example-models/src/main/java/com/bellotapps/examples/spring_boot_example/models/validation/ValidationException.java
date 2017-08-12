package com.bellotapps.examples.spring_boot_example.models.validation;

import java.util.List;

/**
 * A {@link RuntimeException} that represents an entity validation error.
 */
public class ValidationException extends RuntimeException {

    /**
     * A {@link List} containing all {@link ValidationError}s that caused this exception to be thrown.
     */
    private final List<ValidationError> errorList;


    /**
     * @param message   The detail message. The detail message is saved for
     *                  later retrieval by the {@link #getMessage()} method.
     * @param errorList A {@link List} containing all {@link ValidationError}s that caused the exception to be thrown.
     */
    /* package */ ValidationException(String message, List<ValidationError> errorList) {
        super(message);
        this.errorList = errorList;
    }

    /**
     * @param cause     the cause (which is saved for later retrieval by the {@link #getCause()} method).
     *                  For more information, see {@link RuntimeException#RuntimeException(Throwable)}.
     * @param errorList A {@link List} containing all {@link ValidationError}s that caused the exception to be thrown.
     */
    /* package */ ValidationException(Throwable cause, List<ValidationError> errorList) {
        super(cause);
        this.errorList = errorList;
    }

    /**
     * @param errorList A {@link List} containing all {@link ValidationError}s that caused the exception to be thrown.
     */
    /* package */ ValidationException(List<ValidationError> errorList) {
        super();
        this.errorList = errorList;
    }
}
