package com.platformops.user_service.controller;

import com.platformops.user_service.dto.UpdateUserRequest;
import com.platformops.user_service.model.User;
import com.platformops.user_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "Endpoints for managing user accounts and profiles")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(
            summary = "Get all users",
            description = "Retrieve a paginated list of all registered users in the system. Useful for admin dashboards or user management interfaces."
    )
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get user by ID",
            description = "Retrieve details of a specific user by their unique ID. Useful for displaying user profiles or editing user information."
    )
    public User getUserById(
            @Parameter(description = "ID of the user to retrieve", example = "1")
            @PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/me")
    @Operation(
            summary = "Get current user profile",
            description = "Retrieve the profile of the currently authenticated user"
    )
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedEmail = authentication.getName();

        User user = userService.getUserByEmail(authenticatedEmail);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }

        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a user",
            description = "Update the details of the authenticated user. Users can only update their own profile."
    )
    public ResponseEntity<?> updateUser(
            @Parameter(description = "ID of the user to update", example = "1")
            @PathVariable Long id,
            @Parameter(description = "User update request with name, email, and password", required = true)
            @RequestBody UpdateUserRequest request) {

        User updatedUser = userService.updateUser(id, request);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a user",
            description = "Remove the authenticated user's account from the system. Users can only delete their own account."
    )
    public ResponseEntity<?> deleteUser(
            @Parameter(description = "ID of the user to delete", example = "1")
            @PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
