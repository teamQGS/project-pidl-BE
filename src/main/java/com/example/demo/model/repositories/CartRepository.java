package com.example.demo.model.repositories;

import com.example.demo.model.entities.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Locale;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Locale> {
    public Optional<CartEntity> findByUsername(String username);
    public void deleteCartEntityByUsername(String username);
}
