package com.example.demo.model.Repositories;

import com.example.demo.model.Entities.WarehouseEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends MongoRepository<WarehouseEntity, ObjectId> {
}
