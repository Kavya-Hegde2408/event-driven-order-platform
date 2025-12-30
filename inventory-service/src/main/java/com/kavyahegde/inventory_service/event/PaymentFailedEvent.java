package com.kavyahegde.inventory_service.event;

public record PaymentFailedEvent(Long orderId, String reason) {
}
