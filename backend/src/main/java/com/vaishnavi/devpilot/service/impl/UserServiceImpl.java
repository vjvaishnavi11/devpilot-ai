package com.vaishnavi.devpilot.service.impl;

import com.vaishnavi.devpilot.dto.RegisterRequest;
import com.vaishnavi.devpilot.dto.UpdateUserRequest;
import com.vaishnavi.devpilot.dto.UserResponse;
import com.vaishnavi.devpilot.entity.User;
import com.vaishnavi.devpilot.enums.Role;
import com.vaishnavi.devpilot.exception.ResourceAlreadyExistsException;
import com.vaishnavi.devpilot.exception.ResourceNotFoundException;
import com.vaishnavi.devpilot.repository.UserRepository;
import com.vaishnavi.devpilot.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private User getUser(Long id){
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + id
                        ));
    }

    @Transactional
    @Override
    public UserResponse register(RegisterRequest request){

        log.info("Registering with email {}", request.getEmail());

        if(userRepository.existsByEmail(request.getEmail())){
            throw new ResourceAlreadyExistsException("Email already exists");
        }

        if(userRepository.existsByUsername(request.getUsername())){
            throw new ResourceAlreadyExistsException("Username already exists");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        User saved = userRepository.save(user);

        log.info("User registered successfully with id {}", saved.getId());

        return mapToUserResponse(saved);

    }

    private UserResponse mapToUserResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }

    @Override
    public UserResponse getUserById(Long id){

        User user = getUser(id);

        return mapToUserResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(this::mapToUserResponse)
                .toList();

    }

    @Transactional
    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest request){

        log.info("Updating user {}", id);
        User user = getUser(id);

        user.setFullName(request.getFullName());

        if(!user.getUsername().equals(request.getUsername())
            && userRepository.existsByUsername(request.getUsername())){

            throw new ResourceAlreadyExistsException("Username already exists");
        }

        User updatedUser = userRepository.save(user);

        return mapToUserResponse(updatedUser);
    }

    @Transactional
    @Override
    public void deleteUser(Long id){

        log.info("Deleting user {}", id);

        User user = getUser(id);
        userRepository.delete(user);
    }
}
