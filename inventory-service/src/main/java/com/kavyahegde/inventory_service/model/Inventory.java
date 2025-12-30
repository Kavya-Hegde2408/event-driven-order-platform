package com.kavyahegde.inventory_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Inventory {

    @Id
    private Long orderId;

    private String status; // RESERVED, CONFIRMED, RELEASED

    protected Inventory() {}

    public Inventory(Long orderId) {
        this.orderId = orderId;
        this.status = "RESERVED";
    }

    public void confirm() {
        this.status = "CONFIRMED";
    }

    public void release() {
        this.status = "RELEASED";
    }

    // getters
}
