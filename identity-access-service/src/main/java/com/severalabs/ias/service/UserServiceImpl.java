package com.severalabs.ias.service;

import com.severalabs.ias.domain.Role;
import com.severalabs.ias.domain.User;
import com.severalabs.ias.dto.UserRegistrationDTO;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService{
    @Override
    public User createUser(UserRegistrationDTO userRegistrationDTO) {

        User newUser = new User();
        newUser.setEmail(userRegistrationDTO.getEmail());
        newUser.setPassword(userRegistrationDTO.getPassword());
        Set<Role> roles = newUser.getRoles();
        Role role = new Role();
        role.setId(1L);
        role.setName("USER");
        roles.add(role);
        newUser.setRoles(roles);
        return newUser;
    }

    @Override
    public User findByEmail(String email) {
        User user = new User();
        user.setEmail(email);
        return user;
    }
}
