package com.example.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="shipper")
public class Shipper {
    @Id
    @Column(name="shipper_id")
    private String shipperId;

    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

    @Column(name="kakao_user_id")
    private String kakaoUserId;

    @Column(name="address")
    private String address;
}
