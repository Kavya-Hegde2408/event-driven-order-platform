package com.kavyahegde.loyalty_service.service;

import com.kavyahegde.loyalty_service.dto.OrderResponse;
import com.kavyahegde.loyalty_service.event.PaymentSuccessEvent;
import com.kavyahegde.loyalty_service.model.Loyalty;
import com.kavyahegde.loyalty_service.repository.LoyaltyRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class LoyaltyEventListener {

    private final WebClient webClient;
    private final LoyaltyRepository repository;

    public LoyaltyEventListener(WebClient webClient,
                                LoyaltyRepository repository) {
        this.webClient = webClient;
        this.repository = repository;
    }

    @KafkaListener(topics = "payment-success")
    public void onPaymentSuccess(PaymentSuccessEvent event) {

        OrderResponse order =
                webClient.get()
                        .uri("/orders/{id}", event.orderId())
                        .header("Authorization", "Bearer system-token")
                        .retrieve()
                        .bodyToMono(OrderResponse.class)
                        .block();

        int points = order.totalAmount().intValue() / 100 * 10;

        Loyalty loyalty = repository
                .findById(order.userId())
                .orElse(new Loyalty(order.userId(), 0));

        loyalty.addPoints(points);
        repository.save(loyalty);
    }
}
