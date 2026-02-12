package com.severalabs.ias.service;

import com.severalabs.ias.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PasswordRestServiceImpl implements PasswordResetService {

    private final UserRepository userRepository;

    private final ApplicationEventPublisher publisher;

    @Override
    public void requestReset(String email) {

        userRepository.findByEmail(email).ifPresent( user -> {
            
        });

    }

    @Override
    public void resetPassword(String token, String newPassword) {

    }
}
