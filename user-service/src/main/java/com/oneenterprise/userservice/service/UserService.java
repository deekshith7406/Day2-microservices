package com.oneenterprise.userservice.service;

import com.oneenterprise.userservice.dto.UserResponse;
import com.oneenterprise.userservice.exception.UserNotFoundException;
import com.oneenterprise.userservice.model.User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Owns user data. Per the Day 1 handbook, this is intentionally in-memory —
 * no database — so the exercise stays focused on service boundaries and
 * HTTP communication rather than persistence.
 */
@Service
public class UserService {

    private final Map<Long, User> users = new ConcurrentHashMap<>();

    public UserService() {
        // Seed some sample data
        users.put(1L, new User(1L, "Balaji ", "balajis@example.com", "ACTIVE"));
        users.put(2L, new User(2L, "Deekshith ", "deekshith@example.com", "ACTIVE"));
        users.put(3L, new User(3L, "Raina", "raina@example.com", "SUSPENDED"));
    }

    public UserResponse getUserById(Long id) {
        User user = users.get(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        return toDto(user);
    }

    private UserResponse toDto(User user) {
        return new UserResponse(user.getId(), user.getFullName(), user.getEmail());
    }
}
