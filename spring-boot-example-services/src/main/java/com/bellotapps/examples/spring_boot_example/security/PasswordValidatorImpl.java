package com.bellotapps.examples.spring_boot_example.security;

import com.bellotapps.examples.spring_boot_example.error_handling.errros.ValidationError;
import com.bellotapps.examples.spring_boot_example.exceptions.ValidationException;
import com.bellotapps.examples.spring_boot_example.security.PasswordValidator;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Concrete implementation of {@link PasswordValidator}.
 */
@Component
public class PasswordValidatorImpl implements PasswordValidator {


    @Override
    public void validate(CharSequence password) throws ValidationException {
        if (password == null) {
            throw new ValidationException(Collections.singletonList(MISSING_PASSWORD));
        }
        // TODO: check password
    }


    private static final ValidationError MISSING_PASSWORD =
            new ValidationError(ValidationError.ErrorCause.MISSING_VALUE, "password", "The password is missing");
}
