package com.severalabs.ias.service;

import com.severalabs.ias.dto.UserRequest;
import com.severalabs.ias.dto.UserResponse;

import java.util.List;

public interface AdminService {

    UserResponse createUser(UserRequest userRequest);
    void assignRole(Long userId, String role_name);
    void removeRole(Long userId, String role_name);
    void disableUser(Long userId);
    List<UserResponse> listAllUsers();

}
