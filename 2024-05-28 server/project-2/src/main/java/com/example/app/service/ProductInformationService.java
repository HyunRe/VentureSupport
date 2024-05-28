// ProductService.java
package com.example.app.service;

import com.example.app.model.ProductInformation;
import com.example.app.repository.ProductInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductInformationService {

    private final ProductInformationRepository productInformationRepository;

    @Autowired
    public ProductInformationService(ProductInformationRepository productInformationRepository) {
        this.productInformationRepository = productInformationRepository;
    }

    public List<ProductInformation> getAllProductInformation() {
        return productInformationRepository.findAll();
    }

    public Optional<ProductInformation> getProductInformationById(Integer orderId, Integer productId, Integer userId) {
        return productInformationRepository.findById(orderId, productId, userId);
    }

    public ProductInformation createProductInformation(ProductInformation productInformation) {
        return productInformationRepository.save(productInformation);
    }

    public ProductInformation updateProductInformation(ProductInformation productInformation) {
        return productInformationRepository.save(productInformation);
    }

    public void deleteProductInformation(Integer orderId, Integer productId, Integer userId) {
        productInformationRepository.deleteById(orderId, productId, userId);
    }
}
