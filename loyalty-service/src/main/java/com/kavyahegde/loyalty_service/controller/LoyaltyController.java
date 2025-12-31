package com.kavyahegde.loyalty_service.controller;

import com.kavyahegde.loyalty_service.model.Loyalty;
import com.kavyahegde.loyalty_service.repository.LoyaltyRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loyalty")
public class LoyaltyController {

    private final LoyaltyRepository repository;

    public LoyaltyController(LoyaltyRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{userId}")
    public Loyalty getPoints(@PathVariable Long userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));
    }
}
