package com.bellotapps.examples.spring_boot_example.services;

import com.bellotapps.examples.spring_boot_example.interfaces.services.UserService;
import com.bellotapps.examples.spring_boot_example.models.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Concrete implementation of {@link UserService}
 */
@Service
public class UserServiceImpl implements UserService {


    private static final User USER1 = new User("Juan Marcos Bellini", LocalDate.of(1991, 10, 6),
            "juanmbellini", "juanmbellini@hotmail.com", "12345678");
    private static final User USER2 = new User("Mateo Bellini", LocalDate.of(1994, 9, 6),
            "mateo", "mateobellini@hotmail.com", "12345678");

    @Override
    public List<User> getAll() {
        final List<User> users = new LinkedList<>();
        users.add(USER1);
        users.add(USER2);
        return users;
    }

    @Override
    public Optional<User> getById(long id) {
        return Optional.ofNullable(id == 1 ? USER1 : id == 2 ? USER2 : null);
    }
}
