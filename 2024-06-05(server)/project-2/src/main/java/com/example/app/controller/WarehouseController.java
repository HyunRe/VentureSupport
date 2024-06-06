// WarehouseController.java
package com.example.app.controller;

import com.example.app.model.User;
import com.example.app.model.Warehouse;
import com.example.app.repository.UserRepository;
import com.example.app.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    // SELECT (All)
    @GetMapping
    public ResponseEntity<List<Warehouse>> getAllWarehouses() {
        List<Warehouse> warehouseList = warehouseService.getAllWarehouses();
        return new ResponseEntity<>(warehouseList, HttpStatus.OK);
    }

    // SELECT
    @GetMapping("/users/{id}")
    public ResponseEntity<List<Warehouse>> getWarehousesByUserId(@PathVariable Integer id) {
        List<Warehouse> warehouses = warehouseService.getWarehousesByUserId(id);
        if (!warehouses.isEmpty()) {
            return new ResponseEntity<>(warehouses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Warehouse> getWarehouseById(@PathVariable Integer id) {
        Optional<Warehouse> warehouseOptional = warehouseService.getWarehouseById(id);
        if (warehouseOptional.isPresent()) {
            return new ResponseEntity<>(warehouseOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // INSERT
    @PostMapping
    public ResponseEntity<Object> createWarehouse(@RequestBody Warehouse warehouse) {
        // Assuming user_id is provided in the request body
        if (warehouse.getUser() == null || warehouse.getUser().getUserId() == null) {
            return ResponseEntity.badRequest().body("User ID must be provided");
        }

        // Validate user exists
        Optional<User> userOptional = userRepository.findById(warehouse.getUser().getUserId());
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOptional.get();
        warehouse.setUser(user);

        // Save warehouse
        warehouse = warehouseService.saveWarehouse(warehouse);
        return ResponseEntity.status(HttpStatus.CREATED).body(warehouse);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Warehouse> updateWarehouse(@PathVariable Integer id, @RequestBody Warehouse warehouse) {
        Warehouse updatedWarehouse = warehouseService.updateWarehouse(id, warehouse);
        return new ResponseEntity<>(updatedWarehouse, HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable Integer id) {
        warehouseService.deleteWarehouse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
