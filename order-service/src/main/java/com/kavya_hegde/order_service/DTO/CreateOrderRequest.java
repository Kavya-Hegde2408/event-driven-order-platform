package com.kavya_hegde.order_service.DTO;

import java.math.BigDecimal;

public class CreateOrderRequest {
    private Long userId;
    private BigDecimal totalAmount;

    public Long getUserId() {
        return userId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
}
