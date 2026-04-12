package com.mittal.shopping.modules.user.controller;

import com.mittal.shopping.modules.user.dto.UserRegisterRequest;
import com.mittal.shopping.modules.user.dto.UserResponse;
import com.mittal.shopping.modules.user.entity.User;
import com.mittal.shopping.modules.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/auth/register")
    public UserResponse registerUser(@Valid @RequestBody UserRegisterRequest request) {

        return userService.registerUser(request);

    }

    @GetMapping("/getUserByEmail")
    public Optional<User> GetUserByEmail(@Valid @RequestBody String email) {
        Optional<User> a = userService.getUserByEmail(email);
        if (a.isPresent()) {
            return a;
        }
        return null;
    }

    @GetMapping("/getAllUsers")
    public List<User> GetAllUsers() {
        var a = userService.getAllUsers();
        return a;
    }

}
