package com.example.app.controller;

import com.example.app.model.InventoryStatistics;
import com.example.app.model.Product;
import com.example.app.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/product/{productId}/stock")
    public ResponseEntity<Integer> getProductStock(@PathVariable Long productId) {
        Product product = productRepository.findById(productId)
                .orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(product.getQuantity());
    }

    @PutMapping("/product/{productId}/stock")
    public ResponseEntity<String> updateProductStock(@PathVariable Long productId, @RequestParam int quantity) {
        Product product = productRepository.findById(productId)
                .orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        product.setQuantity(quantity);
        productRepository.save(product);
        return ResponseEntity.ok("Product stock updated successfully!");
    }

    @GetMapping("/statistics")
    public ResponseEntity<InventoryStatistics> getInventoryStatistics() {
        long totalProducts = productRepository.count();

        InventoryStatistics statistics = new InventoryStatistics();
        statistics.setTotalProducts((int) totalProducts);

        return ResponseEntity.ok(statistics);
    }
}
