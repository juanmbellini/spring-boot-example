package com.bellotapps.examples.spring_boot_example.error_handling.uniqueness;

import java.util.Arrays;

/**
 * Class representing an error that occurs when setting an already in use value that must be unique.
 * Can also be a group of values.
 */
public class UniqueViolationError {

    /**
     * A helper message to be used for debugging purposes.
     */
    private final String message;

    /**
     * The group of fields that must be unique
     */
    private final String[] uniqueFields;

    /**
     * Constructor.
     *
     * @param message      A helper message to be used for debugging purposes.
     * @param uniqueFields The group of values that must be unique.
     */
    public UniqueViolationError(String message, String... uniqueFields) {
        this.uniqueFields = uniqueFields;
        this.message = message;
    }


    /**
     * @return The group of values that must be unique.
     */
    public String[] getUniqueFields() {
        return Arrays.copyOf(uniqueFields, uniqueFields.length);
    }

    /**
     * @return A helper message to be used for debugging purposes.
     */
    public String getMessage() {
        return message;
    }

}
