package com.severalabs.ias.service;

import com.severalabs.ias.domain.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginAttemptServiceImpl implements LoginAttemptService{

    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final Duration LOCK_DURATION = Duration.ofMinutes(10);

    @Override
    public Boolean isUserLocked(User user) {
        return user.getLockDuration() != null
                && user.getLockDuration().isAfter(Instant.now());
    }

    @Override
    public User loginFailed(User user) {
        int trials = user.getFailedLoginAttempts();
        if ( trials >= MAX_FAILED_ATTEMPTS ) {
            user.setFailedLoginAttempts(0);
            user.setLockDuration(Instant.now().plus(LOCK_DURATION));
        } else
            user.setFailedLoginAttempts(trials + 1);
        return user;
    }

    @Override
    public User loginPassed(User user) {
        user.setFailedLoginAttempts(0);
        user.setLockDuration(null);
        return user;
    }
}
