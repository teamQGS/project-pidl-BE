package com.example.demo.model.Repositories;

import com.example.demo.model.Entities.OrderEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends MongoRepository<OrderEntity, String> {
}
