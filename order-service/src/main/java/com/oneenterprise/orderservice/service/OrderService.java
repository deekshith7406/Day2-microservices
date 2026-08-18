package com.oneenterprise.orderservice.service;

import com.oneenterprise.orderservice.client.UserServiceClient;
import com.oneenterprise.orderservice.dto.OrderResponse;
import com.oneenterprise.orderservice.dto.UserSummary;
import com.oneenterprise.orderservice.exception.OrderNotFoundException;
import com.oneenterprise.orderservice.model.Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OrderService {

    private final Map<Long, Order> orders = new ConcurrentHashMap<>();
    private final UserServiceClient userServiceClient;

    public OrderService(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;

        // Seed sample data — userId 1 and 2 exist in User Service, 99 does not,
        // which is handy for testing the "related user not found" path too.
        orders.put(101L, new Order(101L, 1L, "Mechanical Keyboard", new BigDecimal("89.99")));
        orders.put(102L, new Order(102L, 2L, "Standing Desk", new BigDecimal("349.00")));
        orders.put(103L, new Order(103L, 99L, "Webcam", new BigDecimal("59.50")));
    }

    public OrderResponse getOrderById(Long orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new OrderNotFoundException(orderId);
        }

        // This is the network call — Order Service asking User Service for
        // information it does not own and does not store itself.
        UserSummary user = userServiceClient.getUserById(order.getUserId());

        return new OrderResponse(order.getId(), order.getProduct(), order.getAmount(), user);
    }
}
