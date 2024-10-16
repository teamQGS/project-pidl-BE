package com.example.demo.model.Repositories;

import com.example.demo.model.Entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    public Optional<AddressEntity> findByUsername(String username);
    public void deleteAddressEntityByUsername(String username);
}
