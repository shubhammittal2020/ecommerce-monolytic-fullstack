package com.mittal.shopping.modules.user.controller;

import com.mittal.shopping.common.response.ApiResponse;
import com.mittal.shopping.modules.user.dto.LoginRequest;
import com.mittal.shopping.modules.user.dto.UserRegisterRequest;
import com.mittal.shopping.modules.user.dto.UserResponse;
import com.mittal.shopping.modules.user.entity.User;
import com.mittal.shopping.modules.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/auth/register")
    public ResponseEntity<ApiResponse<UserResponse>> RegisterUser(@Valid @RequestBody UserRegisterRequest request) {
        UserResponse res = userService.registerUser(request);

        return ResponseEntity.ok(
            new ApiResponse<>(
                true,
                "User Registered Successfully",
                res
            )
        );
    }

    @GetMapping("/getUserByEmail")
    public Optional<User> GetUserByEmail(@Valid @RequestBody String email) {
        var a = userService.getUserByEmail(email);
        return a;
    }

    @GetMapping("/getAllUsers")
    public List<User> GetAllUsers() {
        var a = userService.getAllUsers();
        return a;
    }

    @PostMapping("/auth/login")
    public ApiResponse<String> LoginUser(@Valid @RequestBody LoginRequest request) {
        String message = userService.loginUser(request);
        return new ApiResponse<>(
                true,
                message,
                null
        );
    }

}
