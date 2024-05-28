// ProductInformationController.java
package com.example.app.controller;

import com.example.app.model.ProductInformation;
import com.example.app.service.ProductInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product-information")
public class ProductInformationController {

    private final ProductInformationService productInformationService;

    @Autowired
    public ProductInformationController(ProductInformationService productInformationService) {
        this.productInformationService = productInformationService;
    }

    // SELECT (All)
    @GetMapping
    public ResponseEntity<List<ProductInformation>> getAllProductInformation() {
        List<ProductInformation> productInformationList = productInformationService.getAllProductInformation();
        return new ResponseEntity<>(productInformationList, HttpStatus.OK);
    }

    // SELECT
    @GetMapping("/{orderId}/{productId}/{userId}")
    public ResponseEntity<ProductInformation> getProductInformationById(@PathVariable Integer orderId, @PathVariable Integer productId, @PathVariable Integer userId) {
        Optional<ProductInformation> productInformation = productInformationService.getProductInformationById(orderId, productId, userId);
        return productInformation.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // INSERT
    @PostMapping
    public ResponseEntity<ProductInformation> createProductInformation(@RequestBody ProductInformation productInformation) {
        ProductInformation createdProductInformation = productInformationService.createProductInformation(productInformation);
        return new ResponseEntity<>(createdProductInformation, HttpStatus.CREATED);
    }

    // UPDATE
    @PutMapping
    public ResponseEntity<ProductInformation> updateProductInformation(@RequestBody ProductInformation productInformation) {
        ProductInformation updatedProductInformation = productInformationService.updateProductInformation(productInformation);
        return new ResponseEntity<>(updatedProductInformation, HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/{orderId}/{productId}/{userId}")
    public ResponseEntity<Void> deleteProductInformation(@PathVariable Integer orderId, @PathVariable Integer productId, @PathVariable Integer userId) {
        productInformationService.deleteProductInformation(orderId, productId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
