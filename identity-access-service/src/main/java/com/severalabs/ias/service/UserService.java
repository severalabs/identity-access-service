package com.severalabs.ias.service;

import com.severalabs.ias.domain.User;
import com.severalabs.ias.dto.UserRequest;
import com.severalabs.ias.dto.UserResponse;


public interface UserService {

    UserResponse createUser(UserRequest userRequest);

    String loginUser (UserRequest userRequest);

}
