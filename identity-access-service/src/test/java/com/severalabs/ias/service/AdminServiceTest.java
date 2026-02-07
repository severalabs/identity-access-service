package com.severalabs.ias.service;

import com.severalabs.ias.domain.Role;
import com.severalabs.ias.domain.User;
import com.severalabs.ias.dto.UserRequest;
import com.severalabs.ias.dto.UserResponse;
import com.severalabs.ias.repository.RoleRepository;
import com.severalabs.ias.repository.UserRepository;
import com.severalabs.ias.security.services.LoginAttemptService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;


import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@RequiredArgsConstructor
//Tell spring to takeover from junit5 and inject all dependencies
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@ActiveProfiles("test")
public class AdminServiceTest {

    private final AdminService adminService;
    private final UserService userService;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Test
    void createUser_whenUserIsUnique_shouldCreateUser() {

        UserRequest dto = new UserRequest("test@gmail.com", "test@1994");
        UserResponse savedUser = adminService.createUser(dto);
        assertNotNull(savedUser.id());
        assertEquals("test@gmail.com", savedUser.email());
    }

    @Test
    void auditFields_shouldBePopulated() {
        UserRequest dto = new UserRequest("test@gmail.com", "test@1994");
        User user = new User();
        Set<Role> roles = user.getRoles();
        user.setEmail(dto.email());
        user.setPassword(encoder.encode(dto.password()));
        Role role = roleRepository.findByName("USER").orElseThrow();
        roles.add(role);
        user.setRoles(roles);
        user.setEnabled(false);
        user.setCreatedBy("Admin");
        User savedUser = userRepository.save(user);
        assertNotNull(savedUser.getCreatedAt());
        assertNotNull(savedUser.getCreatedBy());
        assertNotNull(savedUser.getUpdatedAt());
    }

    @Test
    void loginUser_whenUserIsDisabled_shouldThrowException() {
        UserRequest dto = new UserRequest("test@gmail.com", "test@1994");
        User user = new User();
        Set<Role> roles = user.getRoles();
        user.setEmail(dto.email());
        user.setPassword(encoder.encode(dto.password()));
        Role role = roleRepository.findByName("USER").orElseThrow();
        roles.add(role);
        user.setRoles(roles);
        user.setEnabled(false);
        userRepository.save(user);
        assertThrows(DisabledException.class, () -> userService.loginUser(dto));
    }

    @Test
    void unlockUser_shouldResetUsersFailedLoginAttemptsAndLockDurationFields() {
        UserRequest newUser = new UserRequest(
                "test@gmail.com", "test@2025");
        userService.createUser(newUser);

        UserRequest wrongUser = new UserRequest(
                "test@gmail.com",
                "testWrongPassword@2025");

        for (int i = 0; i < 6; i++) {
            System.out.println(userService.loginUser(wrongUser));
        }

        User user = userRepository.findByEmail("test@gmail.com").orElseThrow();
        Long id = user.getId();
        adminService.unlockUser(id);
        assertFalse(LoginAttemptService.isUserLocked(user));
    }


}
