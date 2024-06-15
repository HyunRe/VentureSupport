package com.example.app.controller;

import com.example.app.model.VehicleInventory;
import com.example.app.service.VehicleInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicle-inventories")
public class VehicleInventoryController {

    @Autowired
    private VehicleInventoryService vehicleInventoryService;

    @GetMapping
    public List<VehicleInventory> getAllVehicleInventories() {
        return vehicleInventoryService.getAllVehicleInventories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleInventory> getVehicleInventoryById(@PathVariable Integer id) {
        VehicleInventory vehicleInventory = vehicleInventoryService.getVehicleInventoryById(id)
                .orElseThrow(() -> new RuntimeException("VehicleInventory not found with id " + id));
        return ResponseEntity.ok(vehicleInventory);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<VehicleInventory>> getVehicleInventoriesByUserId(@PathVariable Integer id) {
        List<VehicleInventory> vehicleInventories = vehicleInventoryService.getVehicleInventoriesByUserId(id);
        if (!vehicleInventories.isEmpty()) {
            return new ResponseEntity<>(vehicleInventories, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public VehicleInventory createVehicleInventory(@RequestBody VehicleInventory vehicleInventory) {
        return vehicleInventoryService.createVehicleInventory(vehicleInventory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleInventory> updateVehicleInventory(
            @PathVariable Integer id,
            @RequestBody VehicleInventory vehicleInventoryDetails) {
        VehicleInventory updatedVehicleInventory = vehicleInventoryService.updateVehicleInventory(id, vehicleInventoryDetails);
        return ResponseEntity.ok(updatedVehicleInventory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicleInventory(@PathVariable Integer id) {
        vehicleInventoryService.deleteVehicleInventory(id);
        return ResponseEntity.noContent().build();
    }
}
