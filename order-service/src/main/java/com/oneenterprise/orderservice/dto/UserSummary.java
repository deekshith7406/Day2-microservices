package com.oneenterprise.orderservice.dto;

/**
 * Shape of the user data Order Service receives FROM User Service over HTTP.
 * This is deliberately Order Service's own class, not a shared library import —
 * each service defines the contract from its own point of view. Order Service
 * never touches User Service's database or internal model.
 */
public class UserSummary {

    private Long id;
    private String fullName;
    private String email;

    public UserSummary() {
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
