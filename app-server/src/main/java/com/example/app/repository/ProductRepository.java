package com.example.app.repository;

import com.example.app.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByName(String name);

    List<Product> findByPrice(double price);

    List<Product> findByQuantityGreaterThanEqual(int quantity);

    List<Product> findByQuantityLessThanEqual(int quantity);

    List<Product> findByQuantityBetween(int minQuantity, int maxQuantity);

    List<Product> findByNameAndPrice(String name, double price);

}
