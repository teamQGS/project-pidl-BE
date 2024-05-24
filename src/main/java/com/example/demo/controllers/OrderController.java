package com.example.demo.controllers;


import com.example.demo.DTOS.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.services.OrderService;
import java.util.List;

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