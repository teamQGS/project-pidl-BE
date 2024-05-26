package com.example.demo.model.Repositories;

import com.example.demo.model.Entities.AddressEntity;
import com.example.demo.model.Entities.CartEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends MongoRepository<AddressEntity, String> {
    public Optional<AddressEntity> findByUsername(String username);
    public Optional<AddressEntity> deleteAddressEntityByUsername(String username);
}
