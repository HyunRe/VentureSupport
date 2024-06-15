package com.example.app.controller;

import com.example.app.model.Supplier;
import com.example.app.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        List<Supplier> supplierList = supplierService.getAllSuppliers();
        return new ResponseEntity<>(supplierList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable Integer id) {
        Optional<Supplier> supplier = supplierService.getSupplierById(id);
        return supplier.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Supplier> createSupplier(@RequestBody Supplier supplier) {
        Supplier createdSupplier = supplierService.createSupplier(supplier);
        return new ResponseEntity<>(createdSupplier, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable Integer id, @RequestBody Supplier supplierDetails) {
        Optional<Supplier> supplier = supplierService.getSupplierById(id);
        if (supplier.isPresent()) {
            Supplier existingSupplier = supplier.get();
            existingSupplier.setSupplierName(supplierDetails.getSupplierName());
            existingSupplier.setSupplierPhoneNumber(supplierDetails.getSupplierPhoneNumber());
            existingSupplier.setSupplierLocation(supplierDetails.getSupplierLocation());
            Supplier updatedSupplier = supplierService.saveSupplier(existingSupplier);
            return ResponseEntity.ok(updatedSupplier);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Integer id) {
        supplierService.deleteSupplier(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
