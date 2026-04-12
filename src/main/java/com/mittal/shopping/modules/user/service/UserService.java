package com.mittal.shopping.modules.user.service;

import com.mittal.shopping.modules.user.dto.UserRegisterRequest;
import com.mittal.shopping.modules.user.dto.UserResponse;
import com.mittal.shopping.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.mittal.shopping.modules.user.entity.User;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public boolean isEmailExist(String emailId) {
        // TODO: Check user in the database
        //userRepository.findOne(emailId);
        return true;
    }

    public boolean matchCredentials (String emailID, String password) {
        // TODO: Check password for the email
        return true;
    }

    public boolean validateUser(String emailId, String password) {
        if (!isEmailExist(emailId)) {
            return false;
        }
        else if (matchCredentials(emailId, password)) {
            return true;
        }
        return false;
    }

    public UserResponse registerUser(UserRegisterRequest request) {
        UserResponse userResponse = new UserResponse();
        if (!isEmailExist(request.getEmail())) {
            // TODO: Save user to the database
            User user = new User();
            userRepository.save(user);

            log.info("User registered successfully");
        }
        else {
            log.info("email id is not available");
        }

        userResponse.setId(0L);
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

}
