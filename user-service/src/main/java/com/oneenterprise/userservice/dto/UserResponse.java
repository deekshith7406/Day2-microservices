package com.oneenterprise.userservice.dto;

/**
 * API CONTRACT — GET /api/users/{id} (200 response body)
 * ------------------------------------------------------
 * This is the object User Service promises to its consumers. It is
 * independent of the internal {@link com.oneenterprise.userservice.model.User}
 * model and of how (or whether) the data is persisted.
 *
 * Fields:
 *   id       — Long, the user's identifier
 *   fullName — String, display name
 *   email    — String, contact email
 *
 * Internal-only fields (e.g. account "status") are intentionally left out —
 * a consumer should never need to guess whether a field is safe to rely on.
 * Changing this class is a contract change; changing User (the internal
 * model) is not.
 */
public class UserResponse {

    private Long id;
    private String fullName;
    private String email;

    public UserResponse() {
    }

    public UserResponse(Long id, String fullName, String email) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
