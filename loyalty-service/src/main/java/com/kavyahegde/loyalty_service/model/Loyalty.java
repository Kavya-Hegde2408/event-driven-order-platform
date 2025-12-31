package com.kavyahegde.loyalty_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Loyalty {

    @Id
    private Long userId;
    private int points;

    protected Loyalty() {}

    public Loyalty(Long userId, int points) {
        this.userId = userId;
        this.points = points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public Long getUserId() {
        return userId;
    }

    public int getPoints() {
        return points;
    }
}
