package com.bellotapps.examples.spring_boot_example.services;

import com.bellotapps.examples.spring_boot_example.error_handling.errros.ValidationError;
import com.bellotapps.examples.spring_boot_example.error_handling.helpers.ValidationExceptionThrower;
import com.bellotapps.examples.spring_boot_example.error_handling.helpers.ValidationHelper;
import com.bellotapps.examples.spring_boot_example.exceptions.InvalidCredentialsException;
import com.bellotapps.examples.spring_boot_example.exceptions.ValidationException;
import com.bellotapps.examples.spring_boot_example.models.User;
import com.bellotapps.examples.spring_boot_example.persistence.daos.UserDao;
import com.bellotapps.examples.spring_boot_example.security.PasswordValidatorImpl;
import com.bellotapps.examples.spring_boot_example.security.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * Concrete implementation of {@link AuthenticationService}.
 */
@Service
@Transactional(readOnly = true)
public class AuthenticationServiceImpl implements AuthenticationService, ValidationExceptionThrower {

    /**
     * DAO for retreiving {@link User}s data.
     */
    private final UserDao userDao;

    /**
     * {@link PasswordEncoder} used for hashing passwords when authenticating a {@link User}.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * A {@link TokenGenerator} to create tokens when login is performed.
     */
    private final TokenGenerator tokenGenerator;

    @Autowired
    public AuthenticationServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, TokenGenerator tokenGenerator) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public UserAndTokenContainer login(String username, String password) throws InvalidCredentialsException {
        validateCredentials(username, password);
        final User user = userDao.findByUsername(username)
                .orElseThrow(() -> new InvalidCredentialsException("Unknown user"));
        final boolean validPassword = passwordEncoder.matches(password, user.getHashedPassword());
        if (!validPassword) {
            throw new InvalidCredentialsException("Password does not match");
        }
        final String token = tokenGenerator.generate(user);

        return new UserAndTokenContainer(user, token);
    }

    // ================================
    // Helpers
    // ================================

    /**
     * Validates the given credentials.
     *
     * @param username The username to be validated.
     * @param password The password to be validated.
     * @throws ValidationException If the credentials are not valid.
     */
    private void validateCredentials(String username, String password) throws ValidationException {
        final List<ValidationError> errorList = new LinkedList<>();
        ValidationHelper.objectNotNull(username, errorList, MISSING_USERNAME);
        ValidationHelper.objectNotNull(password, errorList, PasswordValidatorImpl.MISSING_PASSWORD);

        throwValidationException(errorList);
    }

    private static final ValidationError MISSING_USERNAME =
            new ValidationError(ValidationError.ErrorCause.MISSING_VALUE, "The username is missing", "username");
}
