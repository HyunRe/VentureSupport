package com.example.app.model;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "shipperment")
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipment_id")
    private Long shipmentId;

    @Column(name = "shipment_date")
    private Date shipmentDate;

    @Column(name = "shipment_client_name")
    private String clientName;

    @Column(name = "shipment_client_number")
    private String clientNumber;

    @Column(name = "shipmet_client_location")
    private String clientLocation;

    @Column(name = "shipment_product_name")
    private String productName;

    @Column(name = "shipmet_product_price")
    private String productPrice;

    @Column(name = "shipment_product_quantity")
    private String productQuantity;

    @Column(name = "shipmeny_product_totalprice")
    private String productTotalPrice;

    @Column(name = "shipment_salary")
    private Integer shipmentSalary;

    // Constructors
    public Shipment() {
    }

    public Shipment(Date shipmentDate, String clientName, String clientNumber, String clientLocation,
                    String productName, String productPrice, String productQuantity, String productTotalPrice,
                    Integer shipmentSalary) {
        this.shipmentDate = shipmentDate;
        this.clientName = clientName;
        this.clientNumber = clientNumber;
        this.clientLocation = clientLocation;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productTotalPrice = productTotalPrice;
        this.shipmentSalary = shipmentSalary;
    }

    // Getters and setters
    public Long getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Date getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(Date shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }

    public String getClientLocation() {
        return clientLocation;
    }

    public void setClientLocation(String clientLocation) {
        this.clientLocation = clientLocation;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(String productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public Integer getShipmentSalary() {
        return shipmentSalary;
    }

    public void setShipmentSalary(Integer shipmentSalary) {
        this.shipmentSalary = shipmentSalary;
    }
}
