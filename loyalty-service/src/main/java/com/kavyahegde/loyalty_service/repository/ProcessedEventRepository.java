package com.kavyahegde.loyalty_service.repository;

import com.kavyahegde.loyalty_service.model.ProcessedEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedEventRepository
        extends JpaRepository<ProcessedEvent, String> {
}
