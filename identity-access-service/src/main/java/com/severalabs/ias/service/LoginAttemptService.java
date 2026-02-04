package com.severalabs.ias.service;

import com.severalabs.ias.domain.User;

public interface LoginAttemptService {

    Boolean isUserLocked(User user);

    void loginFailed (User user);

    void loginPassed (User user);
}
