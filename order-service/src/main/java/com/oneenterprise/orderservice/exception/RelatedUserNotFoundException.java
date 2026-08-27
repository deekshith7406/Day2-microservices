package com.oneenterprise.orderservice.exception;

public class RelatedUserNotFoundException extends RuntimeException {

    public RelatedUserNotFoundException(Long userId) {
        super("Referenced user not found in User Service, id: " + userId);
    }
}
