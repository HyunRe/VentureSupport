package com.example.app.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Embeddable
public class ProductInformationId implements Serializable {

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "user_id")
    private Integer userId;

    // 생성자, Getter 및 Setter
    public ProductInformationId() {}

    public ProductInformationId(Integer orderId, Integer productId, Integer userId) {
        this.orderId = orderId;
        this.productId = productId;
        this.userId = userId;
    }

    // equals() 및 hashCode() 메서드 오버라이드
}
