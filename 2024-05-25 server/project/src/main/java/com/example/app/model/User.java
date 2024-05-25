package com.example.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "nav_id")
    private String navId;

    // Constructors, getters, setters

    public User(String username, String password, String email, Double latitude, Double longitude, String navId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
        this.navId = navId;
    }
}
