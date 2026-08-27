package com.oneenterprise.orderservice.dto;

import java.math.BigDecimal;


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
