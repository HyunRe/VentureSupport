// ProductRepository.java
package com.example.app.repository;

import com.example.app.model.Order;
import com.example.app.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
