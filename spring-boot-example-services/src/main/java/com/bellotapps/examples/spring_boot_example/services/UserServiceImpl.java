package com.bellotapps.examples.spring_boot_example.services;

import com.bellotapps.examples.spring_boot_example.error_handling.errros.UniqueViolationError;
import com.bellotapps.examples.spring_boot_example.error_handling.helpers.UniqueViolationExceptionThrower;
import com.bellotapps.examples.spring_boot_example.exceptions.NoSuchEntityException;
import com.bellotapps.examples.spring_boot_example.exceptions.UnauthorizedException;
import com.bellotapps.examples.spring_boot_example.interfaces.persistence.daos.UserDao;
import com.bellotapps.examples.spring_boot_example.interfaces.persistence.speficication_creators.UserSpecificationCreator;
import com.bellotapps.examples.spring_boot_example.interfaces.services.UserService;
import com.bellotapps.examples.spring_boot_example.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Concrete implementation of {@link UserService}
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService, UniqueViolationExceptionThrower {

    /**
     * DAO for managing {@link User}s data.
     */
    private final UserDao userDao;

    /**
     * Object in charge of creating {@link org.springframework.data.jpa.domain.Specification} of {@link User}s.
     */
    private final UserSpecificationCreator userSpecificationCreator;

    /**
     * {@link PasswordEncoder} used for hashing passwords when creating a new {@link User}.
     */
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserSpecificationCreator userSpecificationCreator,
                           PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.userSpecificationCreator = userSpecificationCreator;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<User> getAll() {
        return userDao.findAll(new Sort(new Sort.Order(Sort.Direction.ASC, "id")));
    }


    @Override
    public Page<User> findMatching(String fullName, LocalDate minBirthDate, LocalDate maxBirthDate,
                                   String username, String email, Pageable pageable) {
        final Specification<User> matching = userSpecificationCreator
                .create(fullName, minBirthDate, maxBirthDate, username, email);
        return userDao.findAll(matching, pageable);
    }

    @Override
    public Optional<User> getById(long id) {
        return userDao.findById(id);
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userDao.findByEmail(email);
    }


    @Override
    @Transactional
    public User register(String fullName, LocalDate birthDate, String username, String email, String password) {
        final List<UniqueViolationError> errorList = new LinkedList<>();
        checkUsernameUniqueness(username, errorList);
        checkEmailUniqueness(email, errorList);
        throwUniqueViolationException(errorList);

        final User user = new User(fullName, birthDate, username, email, passwordEncoder.encode(password));
        return userDao.save(user);
    }

    @Override
    @Transactional
    public void update(long id, String newFullName, LocalDate newBirthDate) {
        final User user = userDao.findById(id).orElseThrow(NoSuchEntityException::new);
        user.update(newFullName, newBirthDate);
        userDao.save(user);
    }

    @Override
    @Transactional
    public void changeUsername(long id, String newUsername) {
        final List<UniqueViolationError> errorList = new LinkedList<>();
        checkUsernameUniqueness(newUsername, errorList);
        throwUniqueViolationException(errorList);

        final User user = userDao.findById(id).orElseThrow(NoSuchEntityException::new);
        user.changeUsername(newUsername);
        userDao.save(user);
    }

    @Override
    @Transactional
    public void changeEmail(long id, String newEmail) {
        final List<UniqueViolationError> errorList = new LinkedList<>();
        checkEmailUniqueness(newEmail, errorList);
        throwUniqueViolationException(errorList);

        final User user = userDao.findById(id).orElseThrow(NoSuchEntityException::new);
        user.changeEmail(newEmail);
        userDao.save(user);
    }

    @Override
    @Transactional
    public void changePassword(long id, String currentPassword, String newPassword) {
        final User user = userDao.findById(id).orElseThrow(NoSuchEntityException::new);

        // If currentPassword doesn't match with the actual password, do not change it
        if (!passwordEncoder.matches(currentPassword, user.getHashedPassword())) {
            throw new UnauthorizedException("The given current password did not match the user's password");
        }
        user.changePassword(newPassword);
        userDao.save(user);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        userDao.findById(id).ifPresent(userDao::delete);
    }

    @Override
    @Transactional
    public void deleteByUsername(String username) {
        userDao.findByUsername(username).ifPresent(userDao::delete);
    }

    @Override
    @Transactional
    public void deleteByEmail(String email) {
        userDao.findByEmail(email).ifPresent(userDao::delete);
    }


    // ================================
    // Helpers
    // ================================

    /**
     * Checks that no {@link User} exists with the given {@code email},
     * adding the {@link UserServiceImpl#EMAIL_IN_USE} {@link UniqueViolationError} to the given {@code errorList}
     * if it exists.
     *
     * @param email     The email that must be unique.
     * @param errorList A {@link List} of {@link UniqueViolationError}
     *                  that might have occurred before executing the method.
     */
    private void checkEmailUniqueness(String email, List<UniqueViolationError> errorList) {
        if (userDao.existsByEmail(email)) {
            errorList.add(EMAIL_IN_USE);
        }
    }

    /**
     * @param username  The username that must be unique.
     * @param errorList A {@link List} of {@link UniqueViolationError}
     *                  that might have occurred before executing the method.
     */
    private void checkUsernameUniqueness(String username, List<UniqueViolationError> errorList) {
        if (userDao.existsByUsername(username)) {
            errorList.add(USERNAME_IN_USE);
        }
    }

    private static final UniqueViolationError USERNAME_IN_USE =
            new UniqueViolationError("The username is already in use", "username");

    private static final UniqueViolationError EMAIL_IN_USE =
            new UniqueViolationError("The email is already in use", "email");
}
