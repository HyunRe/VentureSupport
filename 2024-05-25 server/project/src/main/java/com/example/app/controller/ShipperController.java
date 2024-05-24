package com.example.app.controller;

import com.example.app.model.Shipper;
import com.example.app.service.ShipperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shippers")
public class ShipperController {

    @Autowired
    private ShipperService shipperService;

    @PostMapping
    public ResponseEntity<Shipper> createShipper(@RequestBody Shipper shipper) {
        Shipper newShipper = shipperService.createShipper(shipper);
        return ResponseEntity.ok(newShipper);
    }

    @GetMapping
    public ResponseEntity<List<Shipper>> getAllShippers() {
        List<Shipper> shippers = shipperService.getAllShippers();
        return ResponseEntity.ok(shippers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shipper> getShipperById(@PathVariable Long id) {
        Shipper shipper = shipperService.getShipperById(id);
        return ResponseEntity.ok(shipper);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Shipper> updateShipper(@PathVariable Long id, @RequestBody Shipper shipper) {
        Shipper updatedShipper = shipperService.updateShipper(id, shipper);
        return ResponseEntity.ok(updatedShipper);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipper(@PathVariable Long id) {
        shipperService.deleteShipper(id);
        return ResponseEntity.noContent().build();
    }
}
