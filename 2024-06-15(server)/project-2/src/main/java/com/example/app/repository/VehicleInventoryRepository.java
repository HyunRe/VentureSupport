package com.example.app.repository;

import com.example.app.model.VehicleInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleInventoryRepository extends JpaRepository<VehicleInventory, Integer> {
    List<VehicleInventory> findByUserUserId(Integer userId);
}
