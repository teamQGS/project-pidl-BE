package com.example.demo.Model.Repositories;

import com.example.demo.Model.Entities.WarehouseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends MongoRepository<WarehouseEntity, String> {
    
}
