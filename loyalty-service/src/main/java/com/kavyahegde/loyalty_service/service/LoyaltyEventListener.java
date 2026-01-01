package com.kavyahegde.loyalty_service.service;

import com.kavyahegde.loyalty_service.dto.OrderResponse;
import com.kavyahegde.loyalty_service.event.PaymentSuccessEvent;
import com.kavyahegde.loyalty_service.model.Loyalty;
import com.kavyahegde.loyalty_service.model.ProcessedEvent;
import com.kavyahegde.loyalty_service.repository.LoyaltyRepository;
import com.kavyahegde.loyalty_service.repository.ProcessedEventRepository;
import jakarta.transaction.Transactional;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class LoyaltyEventListener {

    private final WebClient webClient;
    private final LoyaltyRepository loyaltyRepository;
    private final ProcessedEventRepository processedEventRepository;

    public LoyaltyEventListener(WebClient webClient,
                                LoyaltyRepository loyaltyRepository,
                                ProcessedEventRepository processedEventRepository) {
        this.webClient = webClient;
        this.loyaltyRepository = loyaltyRepository;
        this.processedEventRepository = processedEventRepository;
    }

    @KafkaListener(topics = "payment-success", groupId = "loyalty-service-group")
    @Transactional
    public void onPaymentSuccess(PaymentSuccessEvent event) {

        //Idempotency key
        String eventId = "PAYMENT_SUCCESS_" + event.orderId();

        //Ignore duplicate events
        if (processedEventRepository.existsById(eventId)) {
            return;
        }

        OrderResponse order =
                webClient.get()
                        .uri("/orders/{id}", event.orderId())
                        .header("Authorization", "Bearer system-token")
                        .retrieve()
                        .bodyToMono(OrderResponse.class)
                        .block();

        int points = order.totalAmount().intValue() / 100 * 10;

        Loyalty loyalty = loyaltyRepository
                .findById(order.userId())
                .orElse(new Loyalty(order.userId(), 0));

        loyalty.addPoints(points);
        loyaltyRepository.save(loyalty);

        //Mark event as processed
        processedEventRepository.save(new ProcessedEvent(eventId));
    }
}
