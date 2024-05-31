package com.example.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키
    @Column(name = "product_id") // 상품 ID
    private Integer productId;

    @Column(name = "product_name") // 상품 이름
    private String productName;

    @Column(name = "product_price") // 상품 가격
    private Double productPrice;

    @Column(name = "product_quantity") // 상품 수량
    private String productQuantity;

    // Getter 및 Setter 추가
    public Integer getId() {
        return productId;
    }

    public void setId(Integer id) {
        this.productId = id;
    }

    public String getName() {
        return productName;
    }

    public void setName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return productPrice;
    }

    public void setPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public String getQuantity() {
        return productQuantity;
    }

    public void setQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }
}
