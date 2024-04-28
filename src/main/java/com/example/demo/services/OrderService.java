package com.example.demo.services;

import com.example.demo.model.Entities.Enums.Status;
import com.example.demo.model.Entities.OrderEntity;
import com.example.demo.model.Repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<OrderEntity> getOrderById(String id) {
        return orderRepository.findById(id);
    }

    public OrderEntity updateOrderStatus(String id, Status status) {
        Optional<OrderEntity> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            OrderEntity order = optionalOrder.get();
            order.setStatus(status);
            return orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Order Not Found: " + id);
        }
    }

    public OrderEntity saveOrder(OrderEntity order) {
        return orderRepository.save(order);
    }
}