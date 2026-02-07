package com.severalabs.ias.service;

import com.severalabs.ias.dto.UserRequest;
import com.severalabs.ias.dto.UserResponse;
import com.severalabs.ias.exception.UserAlreadyExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@RequiredArgsConstructor
//Tell spring to takeover from junit5 and inject all dependencies
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@AutoConfigureTestDatabase(replace =
        AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class UserServiceTest {

    private final UserService userService;

    @Test
    void createUser_whenEmailIsUnique_shouldCreateUser() {

        UserRequest dto = new UserRequest("test@gmail.com", "test@2025");
        UserResponse savedUser = userService.createUser(dto);
        assertNotNull(savedUser.id());
        assertEquals("test@gmail.com", savedUser.email());
        assertTrue(savedUser.enabled());
    }

    @Test
    void createUser_whenEmailAlreadyExists_shouldThrowException() {

        UserRequest dto = new UserRequest("test@gmail.com", "test@2025");
        UserResponse newUser = userService.createUser(dto);
        assertThrows(UserAlreadyExistsException.class,
                () -> userService.createUser(dto));

    }

}
