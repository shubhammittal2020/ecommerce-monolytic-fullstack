package com.mittal.shopping.modules.user.service;

import com.mittal.shopping.modules.user.dto.UserRegisterRequest;
import com.mittal.shopping.modules.user.dto.UserResponse;
import com.mittal.shopping.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.mittal.shopping.modules.user.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

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

        if (!isEmailExist(request.getEmail())) {
            // Save user to the database
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            user.setRole("DEV");
            user.setCreatedAt("WEB");

            userRepository.save(user);

            log.info("User registered successfully");
        }
        else {
            log.info("email id is not available");
        }

        UserResponse userResponse = new UserResponse();
        userResponse.setId(4L);
        userResponse.setName(request.getName());
        userResponse.setEmail(request.getEmail());

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
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
