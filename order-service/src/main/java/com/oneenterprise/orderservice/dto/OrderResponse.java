package com.oneenterprise.orderservice.dto;

import java.math.BigDecimal;

/**
 * API CONTRACT — GET /api/orders/{id} (200 response body)
 * --------------------------------------------------------
 * What the client of Order Service sees. Combines the order's own data
 * with a {@link UserSummary} that Order Service fetched from User Service
 * over HTTP — a nested UserSummary in the response is the observable proof
 * that the two services actually talked to each other.
 *
 * Fields:
 *   orderId — Long
 *   product — String
 *   amount  — BigDecimal
 *   user    — UserSummary, Order Service's own view of the related user
 *             (never User Service's internal or response model directly)
 */
public class OrderResponse {

    private Long orderId;
    private String product;
    private BigDecimal amount;
    private UserSummary user;

    public OrderResponse(Long orderId, String product, BigDecimal amount, UserSummary user) {
        this.orderId = orderId;
        this.product = product;
        this.amount = amount;
        this.user = user;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getProduct() {
        return product;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public UserSummary getUser() {
        return user;
    }
}
