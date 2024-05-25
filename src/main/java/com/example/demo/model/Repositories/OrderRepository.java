package com.example.demo.model.Repositories;

import com.example.demo.model.Entities.Enums.Status;
import com.example.demo.model.Entities.OrderEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface OrderRepository extends MongoRepository<OrderEntity, String> {
    Optional<OrderEntity> findByCustomerUsernameAndStatus(String customerUsername, Status status);
}
