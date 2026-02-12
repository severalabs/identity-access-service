package com.severalabs.ias.service;

import com.severalabs.ias.domain.Role;
import com.severalabs.ias.domain.User;
import com.severalabs.ias.dto.UserRequest;
import com.severalabs.ias.dto.UserResponse;
import com.severalabs.ias.exception.RoleNotCreatedException;
import com.severalabs.ias.exception.UserAlreadyExistsException;
import com.severalabs.ias.exception.UserNotFoundException;
import com.severalabs.ias.repository.RoleRepository;
import com.severalabs.ias.repository.UserRepository;
import com.severalabs.ias.security.jwt.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final LoginAttemptService loginAttemptService;

//----------------------------------------------------------------------------------------------------------------------
    @Override
    public UserResponse createUser(UserRequest signUpRequest) {

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
        User savedUser = userRepository.save(newUser);
        Set<String> newRoles = savedUser.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toCollection(HashSet::new));
        return new UserResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                newRoles,
                savedUser.isEnabled());
    }

//----------------------------------------------------------------------------------------------------------------------
    @Override
    public String loginUser(UserRequest userRequest) {

        if (!userRepository.existsByEmail(userRequest.email()))
            throw new RuntimeException("Wrong username");

        User user = userRepository.findByEmail(userRequest.email())
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        if (loginAttemptService.isUserLocked(user))
            throw new RuntimeException("Account locked until " + user.getLockDuration());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userRequest.email(), userRequest.password()));
        } catch(BadCredentialsException e) {
            User updatedUserFailed = loginAttemptService.loginFailed(user);
            User failedUser = userRepository.save(updatedUserFailed);
            return e.getMessage() + " " + failedUser.getFailedLoginAttempts();
        }
        User updatedUserPassed = loginAttemptService.loginPassed(user);
        User loggedInUser = userRepository.save(updatedUserPassed);

        return jwtService.generateTokenUsingEmail(loggedInUser.getEmail());
    }

//----------------------------------------------------------------------------------------------------------------------

}
