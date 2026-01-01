package com.kavyahegde.loyalty_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ProcessedEvent {

    @Id
    private String eventId;

    protected ProcessedEvent() {}

    public ProcessedEvent(String eventId) {
        this.eventId = eventId;
    }
}
