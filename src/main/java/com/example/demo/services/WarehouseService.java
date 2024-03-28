package com.example.demo.services;

import com.example.demo.DTOS.WarehouseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService {
    @Autowired
    private MongoTemplate mongoTemplate;

//    public WarehouseDTO createWarehouse()
}
