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

    public Optional<ProductInformation> getProductInformationById(Integer id) {
        return productInformationRepository.findById(id);
    }

    public List<ProductInformation> getProductInformationByOrderId(Integer orederId) {
        return productInformationRepository.findByOrderOrderId(orederId);
    }

    public ProductInformation createProductInformation(ProductInformation productInformation) {
        return productInformationRepository.save(productInformation);
    }

    public ProductInformation updateProductInformation(Integer id, ProductInformation productInformationDetails) {
        Optional<ProductInformation> optionalProductInformation = productInformationRepository.findById(id);
        if (optionalProductInformation.isPresent()) {
            ProductInformation productInformation = optionalProductInformation.get();
            productInformation.setOrder(productInformationDetails.getOrder());
            productInformation.setProduct(productInformationDetails.getProduct());
            return productInformationRepository.save(productInformation);
        } else {
            throw new RuntimeException("ProductInformation not found with id " + id);
        }
    }

    public void deleteProductInformation(Integer id) {
        productInformationRepository.deleteById(id);
    }

    public void deleteProductInformationByOrderId(Integer orderId) {
        List<ProductInformation> productInformationList = productInformationRepository.findByOrderOrderId(orderId);
        productInformationRepository.deleteAll(productInformationList);
    }
}
