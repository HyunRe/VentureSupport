package com.example.app.repository;

import com.example.app.model.Company;
import com.example.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Company findByCompanyName(String companyName);
    Company findByCompanyEmail(String companyEmail);
}
