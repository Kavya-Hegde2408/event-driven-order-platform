package com.kavyahegde.inventory_service.service;

import com.kavyahegde.inventory_service.event.OrderCreatedEvent;
import com.kavyahegde.inventory_service.event.PaymentFailedEvent;
import com.kavyahegde.inventory_service.event.PaymentSuccessEvent;
import com.kavyahegde.inventory_service.model.Inventory;
import com.kavyahegde.inventory_service.repository.InventoryRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class InventoryEventListener {

    private final InventoryRepository repo;

    public InventoryEventListener(InventoryRepository repo) {
        this.repo = repo;
    }

    @KafkaListener(
            topics = "order-created",
            properties = {
                    "spring.json.value.default.type=com.kavyahegde.inventory_service.event.OrderCreatedEvent"
            }
    )
    public void reserveStock(OrderCreatedEvent event) {

        Inventory inventory = new Inventory(event.orderId());
        repo.save(inventory);
    }

    @KafkaListener(
            topics = "payment-success",
            properties = {
                    "spring.json.value.default.type=com.kavyahegde.inventory_service.event.PaymentSuccessEvent"
            }
    )
    public void confirmStock(PaymentSuccessEvent event) {

        Inventory inventory = repo.findById(event.orderId())
                .orElseThrow(() -> new IllegalStateException("Inventory not found"));

        inventory.confirm();
        repo.save(inventory);
    }

    @KafkaListener(
            topics = "payment-failed",
            properties = {
                    "spring.json.value.default.type=com.kavyahegde.inventory_service.event.PaymentFailedEvent"
            }
    )
    public void releaseStock(PaymentFailedEvent event) {

        Inventory inventory = repo.findById(event.orderId())
                .orElseThrow(() -> new IllegalStateException("Inventory not found"));

        inventory.release();
        repo.save(inventory);
    }
}
