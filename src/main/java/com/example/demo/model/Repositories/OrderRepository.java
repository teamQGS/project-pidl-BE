package com.example.demo.model.Repositories;

import com.example.demo.model.Entities.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
}
