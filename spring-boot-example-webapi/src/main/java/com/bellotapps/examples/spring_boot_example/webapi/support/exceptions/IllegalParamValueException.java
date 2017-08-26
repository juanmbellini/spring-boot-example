package com.bellotapps.examples.spring_boot_example.webapi.support.exceptions;

import javax.ws.rs.WebApplicationException;
import java.util.Arrays;
import java.util.List;

/**
 * A {@link WebApplicationException} that represents an illegal parameter value error.
 */
public class IllegalParamValueException extends WebApplicationException {

    /**
     * A {@link List} containing all param names that have been set
     * with the illegal values that caused this exception to be thrown.
     */
    private final List<String> conflictingParams;

    /**
     * Default constructor.
     *
     * @param conflictingParams The param names that have been set
     *                          with the illegal values that caused this exception to be thrown.
     */
    public IllegalParamValueException(String... conflictingParams) {
        this(Arrays.asList(conflictingParams));
    }

    /**
     * Default constructor.
     *
     * @param conflictingParams A {@link List} containing all param names that have been set
     *                          with the illegal values that caused this exception to be thrown.
     */
    public IllegalParamValueException(List<String> conflictingParams) {
        super();
        this.conflictingParams = conflictingParams;
    }


    /**
     * Constructor which can set a {@code message}.
     *
     * @param message           The detail message,
     *                          which is saved for later retrieval by the {@link #getMessage()} method.
     * @param conflictingParams The param names that have been set
     *                          with the illegal values that caused this exception to be thrown.
     */
    public IllegalParamValueException(String message, String... conflictingParams) {
        this(message, Arrays.asList(conflictingParams));
    }

    /**
     * Constructor which can set a {@code message}.
     *
     * @param message           The detail message,
     *                          which is saved for later retrieval by the {@link #getMessage()} method.
     * @param conflictingParams A {@link List} containing all param names that have been set
     *                          with the illegal values that caused this exception to be thrown.
     */
    public IllegalParamValueException(String message, List<String> conflictingParams) {
        super(message);
        this.conflictingParams = conflictingParams;
    }

    /**
     * @param message           The detail message,
     *                          which is saved for later retrieval by the {@link #getMessage()} method.
     * @param cause             The cause (which is saved for later retrieval by the {@link #getCause()} method).
     *                          For more information, see {@link RuntimeException#RuntimeException(Throwable)}.
     * @param conflictingParams The param names that have been set
     *                          with the illegal values that caused this exception to be thrown.
     */
    public IllegalParamValueException(String message, Throwable cause, String... conflictingParams) {
        this(message, cause, Arrays.asList(conflictingParams));
    }

    /**
     * @param message           The detail message,
     *                          which is saved for later retrieval by the {@link #getMessage()} method.
     * @param cause             The cause (which is saved for later retrieval by the {@link #getCause()} method).
     *                          For more information, see {@link RuntimeException#RuntimeException(Throwable)}.
     * @param conflictingParams A {@link List} containing all param names that have been set
     *                          with the illegal values that caused this exception to be thrown.
     */
    public IllegalParamValueException(String message, Throwable cause, List<String> conflictingParams) {
        super(message, cause);
        this.conflictingParams = conflictingParams;
    }

    /**
     * @return A {@link List} containing all param names that have been set
     * with the illegal values that caused this exception to be thrown.
     */
    public List<String> getConflictingParams() {
        return conflictingParams;
    }
}
