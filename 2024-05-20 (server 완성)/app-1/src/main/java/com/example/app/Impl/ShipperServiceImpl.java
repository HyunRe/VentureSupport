package com.example.app.Impl;


import com.example.app.model.Shipper;
import com.example.app.repository.ShipperRepository;
import com.example.app.service.ShipperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShipperServiceImpl implements ShipperService {

    @Autowired
    private ShipperRepository shipperRepository;

    @Override
    public Shipper findAvailableShipper() {
        // Implement logic to find an available shipper
        List<Shipper> shippers = shipperRepository.findAll();
        return shippers.isEmpty() ? null : shippers.get(0); 
    }

    @Override
    public List<Shipper> findAll() {
        return shipperRepository.findAll();
    }

    @Override
    public Shipper save(Shipper shipper) {
        return shipperRepository.save(shipper);
    }

    @Override
    public void deleteById(Long id) {
        shipperRepository.deleteById(id);
    }
}
