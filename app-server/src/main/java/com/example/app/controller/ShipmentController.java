package com.example.app.controller;

import com.example.app.model.Shipment;
import com.example.app.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/shipments")
public class ShipmentController {

    @Autowired
    private ShipmentRepository shipmentRepository;

    // API để lấy vị trí hiện tại của đơn hàng
    @GetMapping("/{shipmentId}/current-location")
    public ResponseEntity<String> getCurrentLocation(@PathVariable Long shipmentId) {
        Optional<Shipment> shipmentOptional = shipmentRepository.findById(shipmentId);
        if (shipmentOptional.isPresent()) {
            String currentLocation = shipmentOptional.get().getClientLocation();
            return ResponseEntity.ok(currentLocation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // API để lấy vị trí giao đơn hàng
    @GetMapping("/{shipmentId}/delivery-location")
    public ResponseEntity<String> getDeliveryLocation(@PathVariable Long shipmentId) {
        Optional<Shipment> shipmentOptional = shipmentRepository.findById(shipmentId);
        if (shipmentOptional.isPresent()) {
            // Assume delivery location is not available in the Shipment entity, return empty string for now
            String deliveryLocation = "";
            return ResponseEntity.ok(deliveryLocation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
