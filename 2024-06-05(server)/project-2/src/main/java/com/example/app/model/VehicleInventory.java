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
    @JoinColumn(name = "user_id", referencedColumnName = "user_id") // 외래키: user_id
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id") // 외래키: product_id
    private Product product;

    @Column(name = "vehicleinventory_quantity") // 차량 인벤토리 수량
    private String vehicleInventoryQuantity;

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getVehicleInventoryQuantity() {
        return vehicleInventoryQuantity;
    }

    public void setVehicleInventoryQuantity(String vehicleInventoryQuantity) {
        this.vehicleInventoryQuantity = vehicleInventoryQuantity;
    }
}
