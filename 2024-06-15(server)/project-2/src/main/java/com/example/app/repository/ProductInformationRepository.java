package com.example.app.repository;

import com.example.app.model.ProductInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInformationRepository extends JpaRepository<ProductInformation, Integer> {
    List<ProductInformation> findByOrderOrderId(Integer orderId);
}

