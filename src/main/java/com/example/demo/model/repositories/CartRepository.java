package com.example.demo.model.repositories;

import com.example.demo.model.entities.CartEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends MongoRepository<CartEntity, String> {
    public Optional<CartEntity> findByUsername(String username);
    public void deleteCartEntityByUsername(String username);
}
