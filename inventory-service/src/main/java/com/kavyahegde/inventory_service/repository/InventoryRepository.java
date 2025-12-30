package com.kavyahegde.inventory_service.repository;

import com.kavyahegde.inventory_service.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
}
