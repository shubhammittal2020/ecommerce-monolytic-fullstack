package com.mittal.shopping.modules.user.controller;

import com.mittal.shopping.common.response.ApiResponse;
import com.mittal.shopping.modules.user.dto.LoginRequest;
import com.mittal.shopping.modules.user.dto.UserRegisterRequest;
import com.mittal.shopping.modules.user.dto.UserResponse;
import com.mittal.shopping.modules.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

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

    @GetMapping("/user/getUserByEmail")
    public ResponseEntity<UserResponse> GetUserByEmail(@Valid @RequestBody String email) {

        UserResponse response = userService.getUserByEmail(email);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/debug")
    public String debug(Authentication authentication) {

        System.out.println("User: " + authentication.getName());
        System.out.println("Roles: " + authentication.getAuthorities());

        return authentication.getAuthorities().toString();

    }

    @GetMapping("/user/getAllUsers")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<UserResponse>> GetAllUsers() {

        List<UserResponse> responses = userService.getAllUsers();

        return ResponseEntity.ok(responses);

    }

    @PostMapping("/auth/login")
    public ApiResponse<String> LoginUser(@Valid @RequestBody LoginRequest request) {
        String token = userService.loginUser(request);
        return new ApiResponse<>(
                true,
                "Login Successful",
                token
        );
    }

    @GetMapping("/test")
    public String test(HttpServletRequest request) {

        String email = (String) request.getAttribute("email");

        return "User from token: " + email;

    }

}
