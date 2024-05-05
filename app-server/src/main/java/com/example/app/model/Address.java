package com.example.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="address")
public class Address {
    @Id
    @Column(name="user_id")
    private String userId;

    @Column(name="name_for_address")
    private String nameForAddress;

    @Column(name="address")
    private String address;
}
