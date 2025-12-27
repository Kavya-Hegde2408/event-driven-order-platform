package com.kavyahegde.payment_service.service;

import com.kavyahegde.payment_service.event.OrderCreatedEvent;
import com.kavyahegde.payment_service.event.PaymentSuccessEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

@Service
public class PaymentProcessor {
    private static final Logger log =
            LoggerFactory.getLogger(PaymentProcessor.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PaymentProcessor(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 2000),
            dltTopicSuffix = "-dlt"
    )
    @KafkaListener(topics = "order-created")
    public void processPayment(OrderCreatedEvent event) {

        log.info("Processing payment for orderId={}", event.orderId());


        if (event.totalAmount().intValue() % 2 == 0) {
            log.info("Payment SUCCESS for orderId={}", event.orderId());
            kafkaTemplate.send(
                    "payment-success",
                    event.orderId().toString(),
                    new PaymentSuccessEvent(event.orderId())
            );
        } else {
            log.error("Payment FAILED for orderId={}", event.orderId());
            throw new RuntimeException("Payment failed");
        }
    }
}
