package com.oneenterprise.orderservice.model;

import java.math.BigDecimal;

public class Order {

    private final Long id;
    private final Long userId;
    private final String product;
    private final BigDecimal amount;

    public Order(Long id, Long userId, String product, BigDecimal amount) {
        this.id = id;
        this.userId = userId;
        this.product = product;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getProduct() {
        return product;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
