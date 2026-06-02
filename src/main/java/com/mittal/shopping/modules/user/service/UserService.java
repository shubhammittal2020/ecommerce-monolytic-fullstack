package com.mittal.shopping.modules.user.service;

import com.mittal.shopping.common.security.JwtUtil;
import com.mittal.shopping.modules.auth.enums.Role;
import com.mittal.shopping.modules.user.dto.LoginRequest;
import com.mittal.shopping.modules.user.dto.UserRegisterRequest;
import com.mittal.shopping.modules.user.dto.UserResponse;
import com.mittal.shopping.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.mittal.shopping.modules.user.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtil jwtUtil;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public String loginUser(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new IllegalArgumentException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getEmail());
    }

    public UserResponse registerUser(UserRegisterRequest request) {
        User user = new User();

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        else {
            // Save user to the database
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(Role.USER);

            userRepository.save(user);

            log.info("User registered successfully");
        }

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());

        return userResponse;
    }

    public UserResponse getUserByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException("No user found with the email: " + email)
                );

        return mapToResponse(user);

    }

    public List<UserResponse> getAllUsers() {

        List<User> users = userRepository.findAll();

        List<UserResponse> responses = new ArrayList<>();

        for (User user : users) {
            responses.add(mapToResponse(user));
        }

        return responses;

    }

    public UserResponse mapToResponse(User user) {

        return UserResponse
                .builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

    }

}
