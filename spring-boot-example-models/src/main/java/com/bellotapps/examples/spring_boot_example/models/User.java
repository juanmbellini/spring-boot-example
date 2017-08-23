package com.bellotapps.examples.spring_boot_example.models;

import com.bellotapps.examples.spring_boot_example.models.constants.ValidationConstants;
import com.bellotapps.examples.spring_boot_example.models.constants.ValidationErrorConstants;
import com.bellotapps.examples.spring_boot_example.utils.error_handling.validation.ValidationExceptionThrower;
import com.bellotapps.examples.spring_boot_example.utils.error_handling.validation.ValidationHelper;
import com.bellotapps.examples.spring_boot_example.utils.error_handling.validation.ValidationError;
import com.bellotapps.examples.spring_boot_example.utils.exceptions.ValidationException;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

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


    /* package */ User() {
        // For Hibernate.
    }

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
        final List<ValidationError> errorList = new LinkedList<>();
        update(fullName, birthDate, errorList);
        changeUsername(username, errorList);
        changeEMail(email, errorList);
        changePassword(hashedPassword);

        throwValidationException(errorList); // Throws ValidationException if values were not valid
    }

    /**
     * Updates this user.
     *
     * @param fullName  The new full name for the user.
     * @param birthDate The new birth date for the user.
     * @throws ValidationException In case any value is not a valid one.
     */
    public void update(String fullName, LocalDate birthDate)
            throws ValidationException {
        final List<ValidationError> errorList = new LinkedList<>();
        update(fullName, birthDate, errorList);
        throwValidationException(errorList); // Throws ValidationException if values were not valid
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
     * Changes this user's username.
     *
     * @param username The new username.
     */
    public void changeUsername(String username) {
        final List<ValidationError> errorList = new LinkedList<>();
        changeEMail(username, errorList);
        throwValidationException(errorList); // Throws ValidationException if the username was not valid
    }

    /**
     * Changes this user's email.
     *
     * @param email The new email.
     */
    public void changeEmail(String email) {
        final List<ValidationError> errorList = new LinkedList<>();
        changeEMail(email, errorList);
        throwValidationException(errorList); // Throws ValidationException if the email was not valid
    }

    /**
     * Changes this user's password.
     *
     * @param hashedPassword The new password for the user (must be hashed).
     */
    public void changePassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }


    // ================================
    // equals, hashcode and toString
    // ================================

    /**
     * Equals based on the {@code id}.
     *
     * @param o The object to be compared with.
     * @return {@code true} if they are the equals, or {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        return id == user.id;
    }

    /**
     * @return This user's hashcode, based on the {@code id}.
     */
    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "User: [" +
                "ID: " + id +
                ", Full Name: " + fullName +
                ", Birth Date: " + birthDate +
                ", Username: " + username +
                ", Email: " + email +
                ']';
    }

    // ================================
    // Private setters
    // ================================

    /**
     * Updates this user.
     *
     * @param fullName  The new full name for the user.
     * @param birthDate The new birth date for the user.
     * @param errorList A {@link List} of {@link ValidationError} that might have occurred before executing the method.
     */
    private void update(String fullName, LocalDate birthDate, List<ValidationError> errorList) {
        setIfNoErrors(this, fullName, errorList, User::validateFullName,
                (user, newValue) -> user.fullName = newValue);
        setIfNoErrors(this, birthDate, errorList, User::validateBirthDate,
                (user, newValue) -> user.birthDate = newValue);
    }

    /**
     * Changes this user's username.
     *
     * @param username  The new username.
     * @param errorList A {@link List} of {@link ValidationError} that might have occurred before executing the method.
     */
    private void changeUsername(String username, List<ValidationError> errorList) {
        setIfNoErrors(this, username, errorList, User::validateUsername,
                (user, newValue) -> user.username = newValue);
    }

    /**
     * Changes this user's email.
     *
     * @param email     The new email.
     * @param errorList A {@link List} of {@link ValidationError} that might have occurred before executing the method.
     */
    private void changeEMail(String email, List<ValidationError> errorList) {
        setIfNoErrors(this, email, errorList, User::validateEmail,
                (user, newValue) -> user.email = newValue);
    }


    // ================================
    // Helpers
    // ================================

    /**
     * Changes the given {@code editedUser} according to the given {@code setterAction},
     * only if the given {@code validator} did not add {@link ValidationError}s to the given {@code errorList}
     * when validating the given {@code newValue}.
     *
     * @param editedUser   The {@link User} being edited.
     * @param newValue     The new value to set (if no error occurred).
     * @param errorList    A {@link List} containing {@link ValidationError}
     *                     that might have occurred before executing the method.
     *                     It will hold new {@link ValidationError} that can be detected when executing this method.
     * @param validator    A {@link BiConsumer} that takes the given {@code newValue} and {@code errorList}
     *                     in order to validate the former, adding new {@link ValidationError} to the {@link List}
     *                     if errors are detected.
     * @param setterAction A {@link BiConsumer} that takes the given {@link User}, and the given {@code newValue},
     *                     and sets the latter in the former.
     * @param <T>          The concrete type of the {@code newValue}.
     */
    private static <T> void setIfNoErrors(User editedUser, T newValue, List<ValidationError> errorList,
                                          BiConsumer<T, List<ValidationError>> validator,
                                          BiConsumer<User, T> setterAction) {
        Objects.requireNonNull(errorList, "The error list must not be null!");

        final int amountOfErrors = errorList.size();
        validator.accept(newValue, errorList); // If not valid, the size of errorList will increase
        if (errorList.size() <= amountOfErrors) {
            setterAction.accept(editedUser, newValue);
        }
    }


    // ================================
    // Validations
    // ================================

    /**
     * Validates the {@code fullName}.
     * Will add {@link ValidationError}s to the given {@code errorList} if the {@code fullName} is not valid.
     *
     * @param fullName  The full name to be validated.
     * @param errorList A {@link List} of {@link ValidationError} that might have occurred before executing the method.
     */
    private static void validateFullName(String fullName, List<ValidationError> errorList) {
        Objects.requireNonNull(errorList, "The error list must not be null!");
        ValidationHelper.stringNotNullAndLengthBetweenTwoValues(fullName, ValidationConstants.NAME_MIN_LENGTH,
                ValidationConstants.NAME_MAX_LENGTH, errorList, ValidationErrorConstants.MISSING_FULL_NAME,
                ValidationErrorConstants.FULL_NAME_TOO_SHORT, ValidationErrorConstants.FULL_NAME_TOO_LONG);
    }

    /**
     * Validates the {@code birthDate}.
     * Will add {@link ValidationError}s to the given {@code errorList} if the {@code birthDate} is not valid.
     *
     * @param birthDate The birth date to be validated.
     * @param errorList A {@link List} of {@link ValidationError} that might have occurred before executing the method.
     */
    private static void validateBirthDate(LocalDate birthDate, List<ValidationError> errorList) {
        Objects.requireNonNull(errorList, "The error list must not be null!");
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
    }

    /**
     * Validates the {@code username}.
     * Will add {@link ValidationError}s to the given {@code errorList} if the {@code username} is not valid.
     *
     * @param username  The username to be validated.
     * @param errorList A {@link List} of {@link ValidationError} that might have occurred before executing the method.
     */
    private static void validateUsername(String username, List<ValidationError> errorList) {
        Objects.requireNonNull(errorList, "The error list must not be null!");
        ValidationHelper.stringNotNullAndLengthBetweenTwoValues(username, ValidationConstants.USERNAME_MIN_LENGTH,
                ValidationConstants.USERNAME_MAX_LENGTH, errorList, ValidationErrorConstants.MISSING_USERNAME,
                ValidationErrorConstants.USERNAME_TOO_SHORT, ValidationErrorConstants.USERNAME_TOO_LONG);
    }

    /**
     * Validates the {@code email}.
     * Will add {@link ValidationError}s to the given {@code errorList} if the {@code email} is not valid.
     *
     * @param email     The email to be validated.
     * @param errorList A {@link List} of {@link ValidationError} that might have occurred before executing the method.
     */
    private static void validateEmail(String email, List<ValidationError> errorList) {
        Objects.requireNonNull(errorList, "The error list must not be null!");
        ValidationHelper.checkEmailNotNullAndValid(email, ValidationConstants.EMAIL_MIN_LENGTH,
                ValidationConstants.EMAIL_MAX_LENGTH, errorList, ValidationErrorConstants.MISSING_E_MAIL,
                ValidationErrorConstants.E_MAIL_TOO_SHORT, ValidationErrorConstants.E_MAIL_TOO_LONG,
                ValidationErrorConstants.INVALID_E_MAIL);
    }
}
