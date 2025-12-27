package com.kavyahegde.payment_service.event;

public record PaymentFailedEvent(Long orderId, String reason) {
}
