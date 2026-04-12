package com.mittal.shopping.modules.user.controller;

import com.mittal.shopping.modules.user.dto.UserRegisterRequest;
import com.mittal.shopping.modules.user.dto.UserResponse;
import com.mittal.shopping.modules.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public UserResponse registerUser(@Valid @RequestBody UserRegisterRequest request) {

        return userService.registerUser(request);
        
    }

}
