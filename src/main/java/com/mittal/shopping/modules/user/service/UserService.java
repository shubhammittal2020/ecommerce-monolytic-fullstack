package com.mittal.shopping.modules.user.service;

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

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public boolean isEmailExist(String emailId) {
        // Check user email in the database
        boolean status = userRepository.findByEmail(emailId).isPresent();

        return status;
    }

    public boolean matchCredentials (String emailID, String password) {
        // Check password for the email
        Optional<User> myUser = userRepository.findByEmail(emailID);
        if (myUser.isPresent()) {
            return myUser.get().getPassword().equals(password);
        }

        return false;
    }

    public boolean validateUser(String emailId, String password) {
        return isEmailExist(emailId) && matchCredentials(emailId, password);
    }

    public UserResponse registerUser(UserRegisterRequest request) {
        User user = new User();

        if (isEmailExist(request.getEmail())) {
            log.info("email id is not available");
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new IllegalArgumentException("Email already exists");
            }
        }
        else {
            // Save user to the database
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole("DEV");
            user.setCreatedAt("WEB");

            userRepository.save(user);

            log.info("User registered successfully");
        }

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());

        return userResponse;
    }

    public void loginUser(String emailId, String password) {
        if (validateUser(emailId, password)) {
            log.info("User login successfully");
        }
        else {
            log.info("User does not exist");
        }
    }

    public Optional<User> getUserByEmail(String email) {
        Optional<User> a = userRepository.findByEmail(email);
        if (a.isPresent()) {
            return a;
        }
        return null;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
