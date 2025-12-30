package com.severalabs.ias.service;

import com.severalabs.ias.domain.User;
import com.severalabs.ias.dto.UserRegistrationDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void createUser_whenEmailIsUnique_shouldCreateUser() {

        UserRegistrationDTO dto = new UserRegistrationDTO( );
        dto.setEmail("owen.chosen@gmail.com");
        dto.setPassword("M@kinj@1994");

        User savedUser = userService.createUser(dto);

        assertNotNull(savedUser.getId());
        assertEquals("owen.chosen@gmail.com", savedUser.getEmail());

    }

    @Test
    void createUser_whenEmailAlreadyExists_shouldThrowException() {

        UserRegistrationDTO dto = new UserRegistrationDTO( );
        dto.setEmail("owen.chosen@gmail.com");
        dto.setPassword("M@kinj@1994");

        User savedUser = userService.createUser(dto);

        assertThrows(Exception.class,
                () -> userService.createUser(dto));

    }

    @Test
    void findByEmail_whenUserExists_shouldReturnsUser(){

        String email = "owen.chosen@gmail.com";

        assertNotNull(userService.findByEmail(email));
    }
}
