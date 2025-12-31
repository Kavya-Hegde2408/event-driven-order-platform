package com.kavyahegde.loyalty_service.dto;

import java.math.BigDecimal;

public record OrderResponse(
        Long id,
        Long userId,
        BigDecimal totalAmount
) {}

