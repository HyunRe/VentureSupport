package com.example.app.service;

import com.example.app.model.Shipper;
import com.example.app.repository.ShipperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipperService {

    @Autowired
    private ShipperRepository shipperRepository;

    // Get all shippers
    public List<Shipper> getAllShippers() {
        return shipperRepository.findAll();
    }

    // Get a shipper by ID
    public Shipper getShipperById(Long id) {
        return shipperRepository.findById(id).orElseThrow();
    }

    // Create a new shipper
    public Shipper createShipper(Shipper shipper) {
        return shipperRepository.save(shipper);
    }

    // Update an existing shipper
    public Shipper updateShipper(Long id, Shipper shipperDetails) {
        Shipper shipper = shipperRepository.findById(id).orElseThrow();
        shipper.setName(shipperDetails.getName());
        shipper.setPhone(shipperDetails.getPhone());
        shipper.setAvailable(shipperDetails.isAvailable());
        // Update other fields as needed
        return shipperRepository.save(shipper);
    }

    // Delete a shipper
    public void deleteShipper(Long id) {
        Shipper shipper = shipperRepository.findById(id).orElseThrow();
        shipperRepository.delete(shipper);
    }

    // Find available shippers
    public List<Shipper> findAvailableShippers() {
        return shipperRepository.findByAvailable(true);
    }

    // Find available shipper (single)
    public Shipper findAvailableShipper() {
        List<Shipper> availableShippers = findAvailableShippers();
        if (!availableShippers.isEmpty()) {
            return availableShippers.get(0); // Return the first available shipper
        }
        return null; // Return null if no available shippers found
    }
}
