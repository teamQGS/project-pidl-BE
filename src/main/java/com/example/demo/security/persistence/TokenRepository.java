package com.example.demo.security.persistence;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends MongoRepository<TokenEntity, ObjectId> {

    Optional<TokenEntity> findByToken(String token);
    void deleteByToken(String token);

}
