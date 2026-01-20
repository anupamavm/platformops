package com.platformops.user_service.controller;

import com.platformops.user_service.dto.CreateUserRequest;
import com.platformops.user_service.dto.UpdateUserRequest;
import com.platformops.user_service.model.User;
import com.platformops.user_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "Endpoints for managing user accounts and profiles")
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

    @PostMapping
    @Operation(
            summary = "Create a new user",
            description = "Register a new user in the system. The email must be unique. Useful for user sign-up flows."
    )
    public User createUser(
            @Parameter(description = "User creation request with name, email, and password", required = true)
            @RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a user",
            description = "Update the details of an existing user by their ID. Useful for profile updates or admin edits."
    )
    public User updateUser(
            @Parameter(description = "ID of the user to update", example = "1")
            @PathVariable Long id,
            @Parameter(description = "User update request with name, email, and password", required = true)
            @RequestBody UpdateUserRequest request) {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a user",
            description = "Remove a user from the system by their ID. Useful for user account deletion or admin cleanup."
    )
    public void deleteUser(
            @Parameter(description = "ID of the user to delete", example = "1")
            @PathVariable Long id) {
        userService.deleteUser(id);
    }
}
