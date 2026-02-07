package com.severalabs.ias.service;

import com.severalabs.ias.domain.Role;
import com.severalabs.ias.domain.User;
import com.severalabs.ias.dto.UserRequest;
import com.severalabs.ias.dto.UserResponse;
import com.severalabs.ias.exception.UserNotFoundException;
import com.severalabs.ias.repository.RoleRepository;
import com.severalabs.ias.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService{

    private final PasswordEncoder encoder;

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    @Override
    public UserResponse createUser( UserRequest userRequest ) {
        User user = new User();
        Set<Role> roles = user.getRoles();
        user.setEmail(userRequest.email());
        user.setPassword(encoder.encode(userRequest.password()));
        Role role = roleRepository.findByName("USER").orElseThrow();
        roles.add(role);
        user.setRoles(roles);
        User savedUser = userRepository.save(user);
        Set<String> newRoles = savedUser.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toCollection(HashSet::new));
        return new UserResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                newRoles,
                savedUser.isEnabled());
    }

    @Override
    public void assignRole(Long userId, String role_name) {
        User user = userRepository.findById(userId).orElseThrow();
        Role role = roleRepository.findByName(role_name).orElseThrow();
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public void removeRole(Long userId, String role_name) {
        User user = userRepository.findById(userId).orElseThrow();
        Role role = roleRepository.findByName(role_name).orElseThrow();
        user.getRoles().remove(role);
        userRepository.save(user);
    }

    @Override
    public void disableUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found"));
        user.setEnabled(false);
        userRepository.save(user);
    }

    @Override
    public void unlockUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found"));
        user.setFailedLoginAttempts(0);
        user.setLockDuration(null);
        userRepository.save(user);
    }

    @Override
    public List<UserResponse> listAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getEmail(),
                        user.getRoles().stream()
                                .map(Role::getName)
                                .collect(Collectors.toCollection(HashSet::new)),
                        user.isEnabled()))
                .collect(Collectors.toList());
    }
}
