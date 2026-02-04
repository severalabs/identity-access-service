package com.severalabs.ias.service;

import com.severalabs.ias.domain.User;
import com.severalabs.ias.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountLockedException;
import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class LoginAttemptServiceImpl implements LoginAttemptService{

    private final UserRepository userRepository;

    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final Duration LOCK_DURATION = Duration.ofMinutes(30);

    @Override
    public Boolean isUserLocked(User user) {
        return user.getLockDuration() != null
                && user.getLockDuration().isAfter(Instant.now());
    }

    @Override
    public void loginFailed(User user) {
        if (user.getFailedLoginAttempts() >= MAX_FAILED_ATTEMPTS) {
            user.setFailedLoginAttempts(0);
            user.setLockDuration(Instant.now().plus(LOCK_DURATION));
        } else {
            int attempts = user.getFailedLoginAttempts() + 1;
            user.setFailedLoginAttempts(attempts);
        }
        userRepository.save(user);
    }

    @Override
    public void loginPassed(User user) {
        user.setFailedLoginAttempts(0);
        user.setLockDuration(null);
        userRepository.save(user);
    }
}
