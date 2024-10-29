package com.example.demo.model.repositories;

import com.example.demo.model.entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    public Optional<AddressEntity> findByUsername(String username);
    public void deleteAddressEntityByUsername(String username);
}
