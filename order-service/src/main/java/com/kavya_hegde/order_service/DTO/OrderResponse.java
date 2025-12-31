package com.kavya_hegde.order_service.DTO;

import java.math.BigDecimal;

public record OrderResponse(
        Long id,
        Long userId,
        BigDecimal totalAmount
) {}
