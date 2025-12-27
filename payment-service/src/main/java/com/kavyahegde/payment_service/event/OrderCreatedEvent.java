package com.kavyahegde.payment_service.event;

import java.math.BigDecimal;

public record OrderCreatedEvent(
        Long orderId,
        Long userId,
        BigDecimal totalAmount
) {}