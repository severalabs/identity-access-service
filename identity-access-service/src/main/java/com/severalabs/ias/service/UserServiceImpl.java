package com.severalabs.ias.service;

import com.severalabs.ias.domain.Role;
import com.severalabs.ias.domain.User;
import com.severalabs.ias.dto.UserRequest;
import com.severalabs.ias.exception.RoleNotCreatedException;
import com.severalabs.ias.exception.UserAlreadyExistsException;
import com.severalabs.ias.repository.RoleRepository;
import com.severalabs.ias.repository.UserRepository;
import com.severalabs.ias.security.jwt.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

//----------------------------------------------------------------------------------------------------------------------
    @Override
    public User createUser(UserRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.email()))
            throw new UserAlreadyExistsException("User Already Exists!");
        User newUser = new User();
        Set<Role> userRoles = newUser.getRoles();
        userRoles.add(roleRepository.findByName("USER").orElseThrow(()
                -> new RoleNotCreatedException("Role Not Created")));
        String encodedPassword = encoder.encode(signUpRequest.password());
        newUser.setEmail(signUpRequest.email());
        newUser.setPassword(encodedPassword);
        newUser.setRoles(userRoles);
        return userRepository.save(newUser);
    }

//----------------------------------------------------------------------------------------------------------------------
    @Override
    public String loginUser(UserRequest userRequest) {
        Authentication authObject = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequest.email(), userRequest.password())
        );
        return jwtService.generateTokenUsingEmail(userRequest.email());
    }

//----------------------------------------------------------------------------------------------------------------------

}
