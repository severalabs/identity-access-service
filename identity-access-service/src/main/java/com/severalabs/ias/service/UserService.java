package com.severalabs.ias.service;

import com.severalabs.ias.domain.User;
import com.severalabs.ias.dto.LoginRequest;
import com.severalabs.ias.dto.SignUpRequest;


public interface UserService {

    User createUser(SignUpRequest signUpRequest);

    String loginUser (LoginRequest loginRequest);

}
