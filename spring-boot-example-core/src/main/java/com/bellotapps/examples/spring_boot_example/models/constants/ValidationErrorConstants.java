package com.bellotapps.examples.spring_boot_example.models.constants;


import com.bellotapps.examples.spring_boot_example.error_handling.errros.ValidationError;

import static com.bellotapps.examples.spring_boot_example.error_handling.errros.ValidationError.ErrorCause.ILLEGAL_VALUE;
import static com.bellotapps.examples.spring_boot_example.error_handling.errros.ValidationError.ErrorCause.MISSING_VALUE;

/**
 * Class containing {@link ValidationError} constants to be reused.
 */
public class ValidationErrorConstants {

    public static final ValidationError MISSING_FULL_NAME = new ValidationError(MISSING_VALUE, "fullName",
            "The fullName is missing.");
    public static final ValidationError FULL_NAME_TOO_SHORT = new ValidationError(ILLEGAL_VALUE, "fullName",
            "The fullName is too short.");
    public static final ValidationError FULL_NAME_TOO_LONG = new ValidationError(ILLEGAL_VALUE, "fullName",
            "The fullName is too long.");


    public static final ValidationError MISSING_BIRTH_DATE = new ValidationError(MISSING_VALUE, "birthDate",
            "The birthDate is missing.");
    public static final ValidationError BIRTH_DATE_TOO_LONG_AGO = new ValidationError(ILLEGAL_VALUE, "birthDate",
            "The birthDate is too long ago.");
    public static final ValidationError FUTURE_BIRTH_DATE = new ValidationError(ILLEGAL_VALUE, "birthDate",
            "The birthDate is in the future.");
    public static final ValidationError TOO_YOUNG_USER = new ValidationError(ILLEGAL_VALUE, "birthDate",
            "The birthDate is too actual (i.e user must be older).");


    public static final ValidationError MISSING_USERNAME = new ValidationError(MISSING_VALUE, "username",
            "The username is missing.");
    public static final ValidationError USERNAME_TOO_SHORT = new ValidationError(ILLEGAL_VALUE, "username",
            "The username is too short.");
    public static final ValidationError USERNAME_TOO_LONG = new ValidationError(ILLEGAL_VALUE, "username",
            "The username is too long.");


    public static final ValidationError MISSING_E_MAIL = new ValidationError(MISSING_VALUE, "email",
            "The email is missing.");
    public static final ValidationError E_MAIL_TOO_SHORT = new ValidationError(ILLEGAL_VALUE, "email",
            "The email is too short.");
    public static final ValidationError E_MAIL_TOO_LONG = new ValidationError(ILLEGAL_VALUE, "email",
            "The email is too long.");
    public static final ValidationError INVALID_E_MAIL = new ValidationError(ILLEGAL_VALUE, "email",
            "The email is not valid.");


    public static final ValidationError MISSING_PASSWORD = new ValidationError(MISSING_VALUE, "password",
            "The password is missing.");
    public static final ValidationError PASSWORD_TOO_SHORT = new ValidationError(ILLEGAL_VALUE, "password",
            "The password is too short.");
    public static final ValidationError PASSWORD_TOO_LONG = new ValidationError(ILLEGAL_VALUE, "password",
            "The password is too long.");


    public static final ValidationError MISSING_USER = new ValidationError(MISSING_VALUE, "user",
            "The user is missing.");

    public static final ValidationError MISSING_ROLE = new ValidationError(MISSING_VALUE, "role",
            "The role is missing.");
}
