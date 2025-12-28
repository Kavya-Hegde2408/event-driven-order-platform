package com.kavya_hegde.order_service.service;

import com.kavya_hegde.order_service.event.PaymentFailedEvent;
import com.kavya_hegde.order_service.event.PaymentSuccessEvent;
import com.kavya_hegde.order_service.model.Order;
import com.kavya_hegde.order_service.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventListener {
    private static final Logger log =
            LoggerFactory.getLogger(OrderEventListener.class);

    private final OrderRepository orderRepository;

    public OrderEventListener(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    @KafkaListener(topics = "payment-success")
    public void handlePaymentSuccess(PaymentSuccessEvent event) {

        log.info("Received payment-success for orderId={}", event.orderId());

        Order order = orderRepository.findById(event.orderId())
                .orElseThrow(() -> new IllegalStateException("Order not found"));

        order.markCompleted();
        orderRepository.save(order);
    }

    @KafkaListener(topics = "payment-failed")
    public void handlePaymentFailed(PaymentFailedEvent event) {

        log.info("Received payment-failed for orderId={}", event.orderId());

        Order order = orderRepository.findById(event.orderId())
                .orElseThrow(() -> new IllegalStateException("Order not found"));

        order.markFailed();
        orderRepository.save(order);
    }

}
