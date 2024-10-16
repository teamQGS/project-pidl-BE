package com.example.demo.controllers;


import com.example.demo.DTOS.*;
import org.springframework.core.annotation.Order;
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

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id){
        return new ResponseEntity<>(orderService.findOrderById(id), HttpStatus.OK);
    }

    @GetMapping("/find/{username}")
    public ResponseEntity<OrderDTO> findByCustomerUsername(@PathVariable String username){
        return new ResponseEntity<>(orderService.findActiveOrderByCustomerUsername(username), HttpStatus.OK);
    }

    @PostMapping("/create/{username}")
    public ResponseEntity<OrderDTO> createOrder(@PathVariable String username, @RequestBody AddressDTO addressDTO) {
        OrderDTO createdOrder = orderService.createOrder(username, addressDTO);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @GetMapping("/findAll/{username}")
    public ResponseEntity<List<OrderDTO>> getOrdersByUsername(@PathVariable String username){
        return new ResponseEntity<>(orderService.getOrdersByUsername(username), HttpStatus.OK);
    }

    @PutMapping("/changeStatus/{orderId}")
    public ResponseEntity<OrderDTO> changeStatus(@RequestBody String status, @PathVariable Long orderId){
        return new ResponseEntity<>(orderService.changeStatus(status, orderId), HttpStatus.OK);
    }
}