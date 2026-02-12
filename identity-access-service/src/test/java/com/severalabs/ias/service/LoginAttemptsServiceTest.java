package com.severalabs.ias.security;

import com.severalabs.ias.dto.UserRequest;
import com.severalabs.ias.dto.UserResponse;
import com.severalabs.ias.repository.UserRepository;
import com.severalabs.ias.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional //Rolls back after test for clean DB after every test
//@Commit
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@ActiveProfiles("test")
public class LoginAttemptsServiceTest {

    private final UserService userService;


    @Test
    void isUserLocked_after5failedAttempts_shouldReturnTrue() {
        UserRequest newUser = new UserRequest(
                "test@gmail.com", "test@2025");
        userService.createUser(newUser);

        UserRequest wrongUser = new UserRequest(
                "test@gmail.com",
                "testWrongPassword@2025");

        for (int i = 0; i < 6; i++) {
            System.out.println(userService.loginUser(wrongUser));
        }

        assertThrows(RuntimeException.class, () -> userService.loginUser(wrongUser));
        assertThrows(RuntimeException.class, () -> userService.loginUser(newUser));
    }


}
