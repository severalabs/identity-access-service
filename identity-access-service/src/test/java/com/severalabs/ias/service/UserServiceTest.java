package com.severalabs.ias.service;

import com.severalabs.ias.domain.User;
import com.severalabs.ias.exception.UserAlreadyExistsException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void createUser_whenEmailIsUnique_shouldCreateUser() {

        SignUpRequest dto = new SignUpRequest("owen.chosen@gmail.com", "M@kinj@1994");
        User savedUser = userService.createUser(dto);

        assertNotNull(savedUser.getId());
        assertEquals("owen.chosen@gmail.com", savedUser.getEmail());
        assertNotEquals(dto.password(), savedUser.getPassword());

    }

    @Test
    void createUser_whenEmailAlreadyExists_shouldThrowException() {

        SignUpRequest dto = new SignUpRequest("owen.chosen@gmail.com", "M@kinj@1994");
        userService.createUser(dto);

        assertThrows(UserAlreadyExistsException.class,
                () -> userService.createUser(dto));

    }
}
