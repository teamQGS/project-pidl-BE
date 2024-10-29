package com.example.demo.model.repositories;

import com.example.demo.model.entities.enums.Status;
import com.example.demo.model.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findByCustomerUsernameAndStatusIn(String customerUsername, List<Status> statuses);
    Optional<OrderEntity> findByCustomerUsernameAndStatus(String customerUsername, Status statuses);
    List<OrderEntity> findAllByCustomerUsernameAndStatusIn(String customerUsername, List<Status> status);
    List<OrderEntity> findAllByStatusIn(List<Status> statuses);
}
