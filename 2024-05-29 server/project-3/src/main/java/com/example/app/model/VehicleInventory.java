package com.example.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "vehicle inventory")
public class VehicleInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키
    @Column(name = "inventory_id") // 재고 ID
    private Integer inventoryId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false) // 상품 ID (외래키)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // 회원 ID (외래키)
    private User user;

    @Column(name = "quantity") // 적재 수량
    private String quantity;

    // Getters and Setters
    public Integer getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
