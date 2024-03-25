package com.example.demo.model.Repositories;

import com.example.demo.model.Entities.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    
}
