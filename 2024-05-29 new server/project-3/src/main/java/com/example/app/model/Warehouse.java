package com.example.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "warehouses")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키
    @Column(name = "warehouse_id") // 창고 ID
    private Integer warehouseId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // 회원 ID (외래키)
    private User user;

    @Column(name = "warehouse_name") // 창고 이름
    private String warehouseName;

    @Column(name = "warehouse_location") // 창고 위치
    private String warehouseLocation;

    // Getters and Setters
    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseLocation() {
        return warehouseLocation;
    }

    public void setWarehouseLocation(String warehouseLocation) {
        this.warehouseLocation = warehouseLocation;
    }
}
