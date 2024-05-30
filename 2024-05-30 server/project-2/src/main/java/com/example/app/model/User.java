package com.example.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 기본키
    @Column(name = "user_id") // 회원 ID
    private Integer userId;

    @Column(name = "user_name") // 회원 이름
    private String username;

    @Column(name = "user_email", nullable = false) // 회원 이메일
    private String email;

    @Column(name = "user_phone_number") // 회원 전화번호
    private String userPhoneNumber;

    @Column(name = "latitude") // 위도
    private Double lat;

    @Column(name = "longitude") // 경도
    private Double lng;

    @Column(name = "user_password", nullable = false) // 회원 비밀번호
    private String userPassword;

    @Column(name = "Inventory_quantity") // 적재 수량
    private String inventoryQuantity;

    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return userPhoneNumber;
    }

    public void setPhone(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getPassword() {
        return userPassword;
    }

    public void setPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getInventoryQuantity() {
        return inventoryQuantity;
    }

    public void setInventoryQuantity(String inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
    }
}
