package com.example.app.controller;

import com.example.app.model.Product;
import com.example.app.model.ProductInformation;
import com.example.app.service.ProductInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping
    public List<ProductInformation> getAllProductInformation() {
        return productInformationService.getAllProductInformation();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductInformation> getProductInformationById(@PathVariable Integer id) {
        Optional<ProductInformation> productInformation = productInformationService.getProductInformationById(id);
        return productInformation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/orders/{id}/products")
    public ResponseEntity<List<Product>> getProductsByOrderId(@PathVariable Integer id) {
        List<ProductInformation> productInformationList = productInformationService.getProductInformationByOrderId(id);

        if (!productInformationList.isEmpty()) {
            List<Product> products = new ArrayList<>();
            for (ProductInformation info : productInformationList) {
                Product product = info.getProduct();
                products.add(product);
            }
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ProductInformation createProductInformation(@RequestBody ProductInformation productInformation) {
        return productInformationService.createProductInformation(productInformation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductInformation> updateProductInformation(
            @PathVariable Integer id, @RequestBody ProductInformation productInformationDetails) {
        try {
            ProductInformation updatedProductInformation = productInformationService.updateProductInformation(id, productInformationDetails);
            return ResponseEntity.ok(updatedProductInformation);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductInformation(@PathVariable Integer id) {
        productInformationService.deleteProductInformation(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteProductInformationByOrderId(@PathVariable Integer id) {
        productInformationService.deleteProductInformationByOrderId(id);
        return ResponseEntity.noContent().build();
    }
}
