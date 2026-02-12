package com.severalabs.ias.service;

import com.severalabs.ias.domain.User;

public interface LoginAttemptService {

    Boolean isUserLocked(User user);

    User loginFailed(User user);

    User loginPassed(User user);
}
