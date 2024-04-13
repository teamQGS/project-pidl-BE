package com.example.demo.security.persistence;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends MongoRepository<TokenEntity, ObjectId> {

    List<TokenEntity> findAllTokenByUser(ObjectId userdId);

    Optional<TokenEntity> findByToken(String token);

    void deleteByToken(String token);

}
