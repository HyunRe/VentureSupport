// ProductInformationRepository.java
package com.example.app.repository;

import com.example.app.model.ProductInformation;
import com.example.app.model.ProductInformationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductInformationRepository extends JpaRepository<ProductInformation, ProductInformationId> {
    Optional<ProductInformation> findById(ProductInformationId id);
    void deleteById(ProductInformationId id);
}
