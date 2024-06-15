package com.example.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

@Entity
@Table(name = "product_information")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키
    @Column(name = "product_information_id") // ID
    private Integer productInformationId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false) // 주문 ID (외래키)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false) // 상품 ID (외래키)
    private Product product;

    // 생성자, 게터, 세터 등
    public ProductInformation() {
    }

    public Integer getProductInformationId() {
        return productInformationId;
    }

    public void setProductInformationId(Integer productInformationId) {
        this.productInformationId = productInformationId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
