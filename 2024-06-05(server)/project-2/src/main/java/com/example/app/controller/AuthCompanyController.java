package com.example.app.controller;

import com.example.app.model.Company;
import com.example.app.model.User;
import com.example.app.repository.CompanyRepository;
import com.example.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/companies")
public class AuthCompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<String> registerCompany(@RequestBody Company company) {
        if (companyRepository.findByCompanyName(company.getCompanyName()) != null) {
            return ResponseEntity.badRequest().body("Username already taken");
        }
        if (companyRepository.findByCompanyEmail(company.getCompanyEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already registered");
        }
        companyRepository.save(company);
        return ResponseEntity.ok("Company registered successfully");
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<String> loginCompany(@RequestBody Company company) {
        Company foundCompany = companyRepository.findByCompanyEmail(company.getCompanyEmail());
        if (foundCompany == null || !foundCompany.getCompanyPassword().equals(company.getCompanyPassword())) {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }
        return ResponseEntity.ok("Login successful");
    }
}
