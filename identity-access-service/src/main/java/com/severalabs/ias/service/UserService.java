package com.severalabs.ias.service;

import com.severalabs.ias.domain.User;
import com.severalabs.ias.dto.UserRequest;


public interface UserService {

    User createUser(UserRequest userRequest);

    String loginUser (UserRequest userRequest);

}
