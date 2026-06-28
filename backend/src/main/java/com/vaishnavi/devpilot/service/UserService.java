package com.vaishnavi.devpilot.service;

import com.vaishnavi.devpilot.dto.RegisterRequest;
import com.vaishnavi.devpilot.dto.UpdateUserRequest;
import com.vaishnavi.devpilot.dto.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse register(RegisterRequest request);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(Long id, UpdateUserRequest request);

    void deleteUser(Long id);
}
