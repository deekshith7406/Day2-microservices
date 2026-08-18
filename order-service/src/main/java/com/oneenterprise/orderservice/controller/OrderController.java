package com.oneenterprise.orderservice.controller;

import com.oneenterprise.orderservice.dto.OrderResponse;
import com.oneenterprise.orderservice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API CONTRACT
 * ------------
 * GET /api/orders/{id}
 *   200 OK                  → body: {@link OrderResponse} (includes the related user)
 *   404 Not Found             → body: ErrorResponse, error = "ORDER_NOT_FOUND"
 *                               (the order id itself doesn't exist)
 *   404 Not Found             → body: ErrorResponse, error = "RELATED_USER_NOT_FOUND"
 *                               (the order exists, but its user doesn't — User Service
 *                                answered, the answer was just "no such user")
 *   503 Service Unavailable    → body: ErrorResponse, error = "USER_SERVICE_UNAVAILABLE"
 *                               (User Service could not be reached, errored, or timed out —
 *                                an infrastructure failure, not a business outcome)
 *   400 Bad Request            → body: ErrorResponse, error = "INVALID_REQUEST"
 *
 * These four cases are deliberately distinct (Day 2 focus: don't collapse a
 * missing resource and a downstream failure into the same response).
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
        OrderResponse response = orderService.getOrderById(id);
        return ResponseEntity.ok(response);
    }
}
