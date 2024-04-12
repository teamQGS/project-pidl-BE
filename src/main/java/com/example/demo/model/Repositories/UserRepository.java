package com.example.demo.model.Repositories;

import com.example.demo.model.Entities.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, ObjectId> {
    Optional<UserEntity> findBy_numericalId(int numericalId);
    Optional<UserEntity> findByUsername(String username);
}
