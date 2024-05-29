// VehicleInventoryController.java
package com.example.app.controller;

import com.example.app.model.VehicleInventory;
import com.example.app.service.VehicleInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vehicle-inventory")
public class VehicleInventoryController {

    private final VehicleInventoryService vehicleInventoryService;

    @Autowired
    public VehicleInventoryController(VehicleInventoryService vehicleInventoryService) {
        this.vehicleInventoryService = vehicleInventoryService;
    }

    // SELECT (All)
    @GetMapping
    public ResponseEntity<List<VehicleInventory>> getAllVehicleInventory() {
        List<VehicleInventory> vehicleInventoryList = vehicleInventoryService.getAllVehicleInventory();
        return new ResponseEntity<>(vehicleInventoryList, HttpStatus.OK);
    }

    // SELECT
    @GetMapping("/{id}")
    public ResponseEntity<VehicleInventory> getVehicleInventoryById(@PathVariable Integer id) {
        Optional<VehicleInventory> vehicleInventory = vehicleInventoryService.getVehicleInventoryById(id);
        return vehicleInventory.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // INSERT
    @PostMapping
    public ResponseEntity<VehicleInventory> createVehicleInventory(@RequestBody VehicleInventory vehicleInventory) {
        VehicleInventory createdVehicleInventory = vehicleInventoryService.createVehicleInventory(vehicleInventory);
        return new ResponseEntity<>(createdVehicleInventory, HttpStatus.CREATED);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<VehicleInventory> updateVehicleInventory(@PathVariable Integer id, @RequestBody VehicleInventory vehicleInventory) {
        VehicleInventory updatedVehicleInventory = vehicleInventoryService.updateVehicleInventory(id, vehicleInventory);
        return new ResponseEntity<>(updatedVehicleInventory, HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicleInventory(@PathVariable Integer id) {
        vehicleInventoryService.deleteVehicleInventory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
