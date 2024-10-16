package com.example.demo.model.repositories;

import com.example.demo.model.entities.AddressEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends MongoRepository<AddressEntity, String> {
    public Optional<AddressEntity> findByUsername(String username);
    public Optional<AddressEntity> deleteAddressEntityByUsername(String username);
}
