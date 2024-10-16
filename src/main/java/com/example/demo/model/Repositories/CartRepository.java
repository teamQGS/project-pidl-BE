package com.example.demo.model.Repositories;

import com.example.demo.model.Entities.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Locale;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Locale> {
    public Optional<CartEntity> findByUsername(String username);
    public void deleteCartEntityByUsername(String username);
}
