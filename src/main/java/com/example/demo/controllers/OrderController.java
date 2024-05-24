package com.example.demo.controllers;


import com.example.demo.DTOS.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import com.example.demo.model.Entities.Enums.Role;
import com.example.demo.model.Entities.Enums.Status;
import com.example.demo.security.config.UserAuthProvider;
import com.example.demo.services.OrderService;
import com.example.demo.services.UserService;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.model.Entities.OrderEntity;
import com.example.demo.services.ProductService;
import org.modelmapper.ModelMapper;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable String id){
        return new ResponseEntity<>(orderService.findOrderById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<OrderDTO> createOrder(@RequestParam String username, @RequestBody AddressDTO addressDTO) {
        OrderDTO createdOrder = orderService.createOrder(username, addressDTO);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }
}