package com.severalabs.ias.service;

import com.severalabs.ias.domain.Role;
import com.severalabs.ias.domain.User;
import com.severalabs.ias.dto.UserRequest;
import com.severalabs.ias.dto.UserResponse;
import com.severalabs.ias.repository.RoleRepository;
import com.severalabs.ias.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import javax.naming.AuthenticationException;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void createUser_whenUserIsUnique_shouldCreateUser() {

        UserRequest dto = new UserRequest("test@gmail.com", "test@1994");
        UserResponse savedUser = adminService.createUser(dto);
        assertNotNull(savedUser.id());
        assertEquals("test@gmail.com", savedUser.email());
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


}
