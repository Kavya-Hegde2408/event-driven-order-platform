package com.kavya_hegde.order_service.event;

public record PaymentFailedEvent(Long orderId, String reason) {
}
