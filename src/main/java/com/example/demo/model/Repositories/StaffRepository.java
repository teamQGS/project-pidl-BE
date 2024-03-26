package com.example.demo.Model.Repositories;

import com.example.demo.Model.Entities.StaffEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends MongoRepository<StaffEntity, String> {
    
}
