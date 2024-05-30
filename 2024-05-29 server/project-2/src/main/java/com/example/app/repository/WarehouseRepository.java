// WarehouseRepository.java
package com.example.app.repository;

import com.example.app.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
    List<Warehouse> findByUserUserId(Integer userId);
}
