package com.oneenterprise.orderservice.exception;

/**
 * Thrown when User Service responds with 404 for the user referenced by an order —
 * i.e. the downstream call succeeded, but the user simply doesn't exist.
 */
public class RelatedUserNotFoundException extends RuntimeException {

    public RelatedUserNotFoundException(Long userId) {
        super("Referenced user not found in User Service, id: " + userId);
    }
}
