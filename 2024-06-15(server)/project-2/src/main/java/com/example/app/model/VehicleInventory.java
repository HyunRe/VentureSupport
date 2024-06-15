package com.example.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "vehicle_inventory")
public class VehicleInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키
    @Column(name = "vehicleinventory_id") // 차량 인벤토리 ID
    private Integer vehicleInventoryId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // 회원 ID (외래키면서 기본키)
    private User user;

    @Column(name = "vehicleinventory_quantity") // 차량 인벤토리 수량
    private String vehicleInventoryQuantity;

    @Column(name = "product_name") // 상품명
    private String productName;

    // Getters and Setters
    public Integer getVehicleInventoryId() {
        return vehicleInventoryId;
    }

    public void setVehicleInventoryId(Integer vehicleInventoryId) {
        this.vehicleInventoryId = vehicleInventoryId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getVehicleInventoryQuantity() {
        return vehicleInventoryQuantity;
    }

    public void setVehicleInventoryQuantity(String vehicleInventoryQuantity) {
        this.vehicleInventoryQuantity = vehicleInventoryQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
