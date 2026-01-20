package com.platformops.user_service.controller;

import com.platformops.user_service.dto.AuthResponse;
import com.platformops.user_service.dto.LoginRequest;
import com.platformops.user_service.dto.RegisterRequest;
import com.platformops.user_service.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
@SecurityRequirement(name = "") // No authentication required for these endpoints
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @Operation(
            summary = "Register a new user",
            description = "Create a new user account and receive a JWT token for authentication"
    )
    public ResponseEntity<AuthResponse> register(
            @Parameter(description = "Registration details including name, email, and password", required = true)
            @RequestBody RegisterRequest request) {
        try {
            AuthResponse response = authService.register(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    @Operation(
            summary = "Login user",
            description = "Authenticate a user with email and password, and receive a JWT token"
    )
    public ResponseEntity<AuthResponse> login(
            @Parameter(description = "Login credentials including email and password", required = true)
            @RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

