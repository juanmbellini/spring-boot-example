package com.bellotapps.examples.spring_boot_example.models.constants;

import java.time.LocalDate;

/**
 * Constants to be used when validating entities.
 */
public class ValidationConstants {

    // ==================================
    // Minimum values
    // ==================================

    public final static int NAME_MIN_LENGTH = 1;
    public final static LocalDate MIN_BIRTH_DATE = LocalDate.MIN;
    public final static int USERNAME_MIN_LENGTH = 4;
    public final static int EMAIL_MIN_LENGTH = 4;
    /**
     * The minimum age a {@link com.bellotapps.examples.spring_boot_example.models.User} can have.
     */
    public static final int MINIMUM_AGE = 13;


    // ==================================
    // Maximum values
    // ==================================

    public final static int NAME_MAX_LENGTH = 64;
    public final static int USERNAME_MAX_LENGTH = 64;
    public final static int EMAIL_MAX_LENGTH = 254;

}
