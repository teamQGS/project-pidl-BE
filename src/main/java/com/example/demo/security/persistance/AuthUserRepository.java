package com.example.demo.security.persistance;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AuthUserRepository extends MongoRepository<AuthUser, ObjectId> {
    Optional<AuthUser> findByUsername(String username);
}
