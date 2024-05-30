// VehicleInventoryService.java
package com.example.app.service;

import com.example.app.model.VehicleInventory;
import com.example.app.repository.VehicleInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleInventoryService {

    private final VehicleInventoryRepository vehicleInventoryRepository;

    @Autowired
    public VehicleInventoryService(VehicleInventoryRepository vehicleInventoryRepository) {
        this.vehicleInventoryRepository = vehicleInventoryRepository;
    }

    public List<VehicleInventory> getAllVehicleInventory() {
        return vehicleInventoryRepository.findAll();
    }

    public Optional<VehicleInventory> getVehicleInventoryById(Integer id) {
        return vehicleInventoryRepository.findById(id);
    }

    public VehicleInventory createVehicleInventory(VehicleInventory vehicleInventory) {
        return vehicleInventoryRepository.save(vehicleInventory);
    }

    public VehicleInventory updateVehicleInventory(Integer id, VehicleInventory vehicleInventory) {
        Optional<VehicleInventory> optionalVehicleInventory = vehicleInventoryRepository.findById(id);
        if (optionalVehicleInventory.isPresent()) {
            vehicleInventory.setInventoryId(id);
            return vehicleInventoryRepository.save(vehicleInventory);
        } else {
            throw new IllegalArgumentException("Vehicle inventory not found with id: " + id);
        }
    }

    public void deleteVehicleInventory(Integer id) {
        vehicleInventoryRepository.deleteById(id);
    }
}
