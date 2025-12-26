package com.kavya_hegde.order_service.controller;

import com.kavya_hegde.order_service.DTO.CreateOrderRequest;
import com.kavya_hegde.order_service.model.Order;
import com.kavya_hegde.order_service.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(
            @RequestBody CreateOrderRequest request) {

        Order order = orderService.createOrder(
                request.getUserId(),
                request.getTotalAmount()
        );

        return ResponseEntity.ok(order);
    }
}
