package com.bellotapps.examples.spring_boot_example.models;

import com.bellotapps.examples.spring_boot_example.models.validation.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 * Class representing a user of the application.
 */
@Entity
@Table(name = "users", indexes = {
        @Index(name = "users_username_unique_index", columnList = "username", unique = true),
        @Index(name = "users_email_unique_index", columnList = "email", unique = true),

})
public class User implements ValidationExceptionThrower {

    /**
     * The minimum age a user can have.
     */
    private static final int MINIMUM_AGE = 13;


    /**
     * The user's id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    /**
     * The user's full name.
     */
    @Column(name = "full_name")
    private String fullName;

    /**
     * The user's birth date.
     */
    @Column(name = "birth_date")
    private LocalDate birthDate;

    /**
     * The username.
     */
    @Column(name = "username")
    private String username;

    /**
     * The user's email.
     */
    @Column(name = "email")
    private String email;

    /**
     * The user's password.
     */
    @Column(name = "hashed_password")
    private String hashedPassword;


    /**
     * Constructor.
     *
     * @param fullName       The user's full name.
     * @param birthDate      The user's birth date.
     * @param username       The username.
     * @param email          The user's email.
     * @param hashedPassword The user's password (must be hashed).
     * @throws ValidationException In case any value is not a valid one.
     */
    public User(String fullName, LocalDate birthDate, String username, String email, String hashedPassword)
            throws ValidationException {
        update(fullName, birthDate, username, email);
        changePassword(hashedPassword);
    }

    /**
     * Updates this user.
     *
     * @param fullName  The new full name for the user.
     * @param birthDate The new birth date for the user.
     * @param username  The new username for the user.
     * @param email     The new email for the user.
     * @throws ValidationException In case any value is not a valid one.
     */
    public void update(String fullName, LocalDate birthDate, String username, String email)
            throws ValidationException {
        validate(fullName, birthDate, username, email);
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.username = username;
        this.email = email;
    }


    /**
     * @return The user's id.
     */
    public long getId() {
        return id;
    }

    /**
     * @return The user's full name.
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @return The user's birth date.
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return The user's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return The user's password.
     */
    public String getHashedPassword() {
        return hashedPassword;
    }

    /**
     * Changes this user's password.
     *
     * @param hashedPassword The new password for the user (must be hashed).
     */
    public void changePassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }


    // ==================================
    // Validations
    // ==================================

    /**
     * Validates the user
     *
     * @param fullName  The full name to be validated.
     * @param birthDate The birth date to be validated.
     * @param username  The user name to be validated.
     * @param email     The email to be validated.
     * @throws ValidationException In case any value is not a valid one.
     */
    private void validate(String fullName, LocalDate birthDate, String username, String email)
            throws ValidationException {
        final List<ValidationError> errorList = new LinkedList<>();
        // Validate full name
        ValidationHelper.stringNotNullAndLengthBetweenTwoValues(fullName, ValidationConstants.NAME_MIN_LENGTH,
                ValidationConstants.NAME_MAX_LENGTH, errorList, ValidationErrorConstants.MISSING_FULL_NAME,
                ValidationErrorConstants.FULL_NAME_TOO_SHORT, ValidationErrorConstants.FULL_NAME_TOO_LONG);
        // Validate birth date
        if (birthDate == null) {
            errorList.add(ValidationErrorConstants.MISSING_BIRTH_DATE);
        } else {
            if (birthDate.isBefore(ValidationConstants.MIN_BIRTH_DATE)) {
                errorList.add(ValidationErrorConstants.BIRTH_DATE_TOO_LONG_AGO);
            } else if (birthDate.isAfter(LocalDate.now())) {
                errorList.add(ValidationErrorConstants.FUTURE_BIRTH_DATE);
            } else if (birthDate.isAfter(LocalDate.now().minusYears(MINIMUM_AGE))) {
                errorList.add(ValidationErrorConstants.TOO_YOUNG_USER);
            }
        }
        // Validate username
        ValidationHelper.stringNotNullAndLengthBetweenTwoValues(username, ValidationConstants.USERNAME_MIN_LENGTH,
                ValidationConstants.USERNAME_MAX_LENGTH, errorList, ValidationErrorConstants.MISSING_USERNAME,
                ValidationErrorConstants.USERNAME_TOO_SHORT, ValidationErrorConstants.USERNAME_TOO_LONG);
        // Validate email
        ValidationHelper.checkEmailNotNullAndValid(email, ValidationConstants.EMAIL_MIN_LENGTH,
                ValidationConstants.EMAIL_MAX_LENGTH, errorList, ValidationErrorConstants.MISSING_E_MAIL,
                ValidationErrorConstants.E_MAIL_TOO_SHORT, ValidationErrorConstants.E_MAIL_TOO_LONG,
                ValidationErrorConstants.INVALID_E_MAIL);

        // Password must be validated in security layer.

        throwValidationException(errorList); // throws ValidationException if error list is not empty.
    }
}
