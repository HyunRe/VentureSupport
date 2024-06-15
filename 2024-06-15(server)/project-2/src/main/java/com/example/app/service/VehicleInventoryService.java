package com.example.app.service;

import com.example.app.model.VehicleInventory;
import com.example.app.repository.VehicleInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleInventoryService {

    @Autowired
    private VehicleInventoryRepository vehicleInventoryRepository;

    public List<VehicleInventory> getAllVehicleInventories() {
        return vehicleInventoryRepository.findAll();
    }

    public Optional<VehicleInventory> getVehicleInventoryById(Integer id) {
        return vehicleInventoryRepository.findById(id);
    }

    public List<VehicleInventory> getVehicleInventoriesByUserId(Integer userId) {
        return vehicleInventoryRepository.findByUserUserId(userId);
    }

    public VehicleInventory createVehicleInventory(VehicleInventory vehicleInventory) {
        return vehicleInventoryRepository.save(vehicleInventory);
    }

    public VehicleInventory updateVehicleInventory(Integer id, VehicleInventory vehicleInventoryDetails) {
        VehicleInventory vehicleInventory = vehicleInventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("VehicleInventory not found with id " + id));

        vehicleInventory.setUser(vehicleInventoryDetails.getUser());
        vehicleInventory.setVehicleInventoryQuantity(vehicleInventoryDetails.getVehicleInventoryQuantity());
        vehicleInventory.setProductName(vehicleInventoryDetails.getProductName());

        return vehicleInventoryRepository.save(vehicleInventory);
    }


    public void deleteVehicleInventory(Integer id) {
        VehicleInventory vehicleInventory = vehicleInventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("VehicleInventory not found with id " + id));

        vehicleInventoryRepository.delete(vehicleInventory);
    }
}
