package com.kavyahegde.loyalty_service.repository;

import com.kavyahegde.loyalty_service.model.Loyalty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoyaltyRepository extends JpaRepository<Loyalty, Long> {
}
