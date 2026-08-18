package com.oneenterprise.orderservice.exception;

/**
 * Thrown when the HTTP call to User Service fails for infrastructure reasons —
 * connection refused, timeout, DNS failure, 5xx from User Service, etc.
 * This is the exercise's deliberate demonstration that "a network call is not
 * the same as a local Java method call."
 */
public class UserServiceUnavailableException extends RuntimeException {

    public UserServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
