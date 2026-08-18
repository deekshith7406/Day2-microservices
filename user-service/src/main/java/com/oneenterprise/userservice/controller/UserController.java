package com.oneenterprise.userservice.controller;

import com.oneenterprise.userservice.dto.UserResponse;
import com.oneenterprise.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API CONTRACT
 * ------------
 * GET /api/users/{id}
 *   200 OK              → body: {@link UserResponse}
 *   404 Not Found        → body: {@link com.oneenterprise.userservice.exception.ErrorResponse},
 *                           error = "USER_NOT_FOUND"
 *   400 Bad Request       → body: ErrorResponse, error = "INVALID_REQUEST" (e.g. id is not a number)
 *
 * Consumers should be able to rely on this behavior without reading
 * UserService or the User entity.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
}
