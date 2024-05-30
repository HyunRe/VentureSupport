// OrderRepository.java
package com.example.app.repository;

import com.example.app.model.Order;
import com.example.app.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserUserId(Integer userId);

    List<Order> findByCompanyCompanyId(Integer companyId);

    List<Order> findBySupplierSupplierId(Integer supplierId);
}
