package com.example.demo.controllers;

import com.example.demo.DTOS.OrderDTO;
import com.example.demo.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {

    @Autowired
    OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("order/changeStatus/{orderId}")
    public ResponseEntity<OrderDTO> changeRole(@RequestBody String status, @PathVariable String orderId){
        return new ResponseEntity<>(orderService.changeStatus(status, orderId), HttpStatus.OK);
    }
}
