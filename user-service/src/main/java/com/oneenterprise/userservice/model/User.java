package com.oneenterprise.userservice.model;

/**
 * Internal representation of a user — this is the persistence/domain shape,
 * not the API contract. It is deliberately NOT returned directly from the
 * controller. See {@link com.oneenterprise.userservice.dto.UserResponse}
 * for the object that actually crosses the network boundary.
 *
 * Day 2 note: keeping this separation means fields can be added here
 * (e.g. "status", audit timestamps, an internal risk score) without ever
 * silently changing what Order Service — or any other consumer — receives.
 */
public class User {

    private final Long id;
    private final String fullName;
    private final String email;
    private final String status; // e.g. ACTIVE, SUSPENDED — internal detail

    public User(Long id, String fullName, String email, String status) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }
}
