package com.severalabs.ias.service;

import com.severalabs.ias.domain.Role;
import com.severalabs.ias.domain.User;
import com.severalabs.ias.dto.UserRegistrationDTO;
import com.severalabs.ias.exception.RoleNotCreatedException;
import com.severalabs.ias.exception.UserAlreadyExistsException;
import com.severalabs.ias.repository.RoleRepository;
import com.severalabs.ias.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public User createUser(UserRegistrationDTO userRegistrationDTO) {

        if (userRepository.existsByEmail(userRegistrationDTO.getEmail()))
            throw new UserAlreadyExistsException("User Already Exists!");

        User newUser = new User();
        Set<Role> userRoles = newUser.getRoles();
        userRoles.add(roleRepository.findByName("USER").orElseThrow(()
                -> new RoleNotCreatedException("Role Not Created")));
        String encodedPassword = encoder.encode(userRegistrationDTO.getPassword());

        newUser.setEmail(userRegistrationDTO.getEmail());
        newUser.setPassword(encodedPassword);
        newUser.setRoles(userRoles);

        return userRepository.save(newUser);
    }

}
