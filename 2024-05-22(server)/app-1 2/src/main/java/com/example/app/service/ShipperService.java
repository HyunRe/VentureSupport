package com.example.app.service;

import com.example.app.model.Shipper;

import java.util.List;

public interface ShipperService {
    Shipper findAvailableShipper();
    List<Shipper> findAll();
    Shipper save(Shipper shipper);
    void deleteById(Long id);
}
