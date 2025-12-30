package com.severalabs.ias.service;

import com.severalabs.ias.domain.User;
import com.severalabs.ias.dto.UserRegistrationDTO;

public interface UserService {

    User createUser(UserRegistrationDTO userRegistrationDTO);

}
