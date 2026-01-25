package com.severalabs.ias.repository;

import com.severalabs.ias.domain.Role;
import com.severalabs.ias.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace =
        AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void existsByEmail_shouldReturnTrue_whenEmailExists() {

        User user = new User();
        Set<Role> userRoles = user.getRoles();
        userRoles.add(roleRepository.findByName("USER").orElseThrow());

        user.setEmail("test01@gmail.com");
        user.setPassword("test01");
        user.setRoles(userRoles);

        userRepository.save(user);

        assertTrue(userRepository.existsByEmail("test01@gmail.com"));
    }
}
