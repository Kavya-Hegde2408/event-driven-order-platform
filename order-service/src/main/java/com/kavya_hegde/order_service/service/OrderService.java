package com.kavya_hegde.order_service.service;

import com.kavya_hegde.order_service.DTO.OrderResponse;
import com.kavya_hegde.order_service.event.OrderCreatedEvent;
import com.kavya_hegde.order_service.model.Order;
import com.kavya_hegde.order_service.repository.OrderRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private static final String TOPIC = "order-created";

    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public OrderService(OrderRepository orderRepository,
                        KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Order createOrder(Long userId, java.math.BigDecimal amount) {

        Order order = new Order(userId, amount);
        Order savedOrder = orderRepository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(
                savedOrder.getId(),
                savedOrder.getUserId(),
                savedOrder.getTotalAmount()
        );

        kafkaTemplate.send(TOPIC, savedOrder.getId().toString(), event);

        return savedOrder;
    }
    public OrderResponse getOrder(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Order not found: " + id));

        return new OrderResponse(
                order.getId(),
                order.getUserId(),
                order.getTotalAmount()
        );
    }
}
