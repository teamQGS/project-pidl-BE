package com.example.demo.controllers;

import com.example.demo.DTOS.WarehouseDTO;
import com.example.demo.services.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/warehouse")
public class WarehouseController {

    @Autowired
    WarehouseService service;
    @GetMapping
    public ResponseEntity<List<WarehouseDTO>> getWarehouseInfo(){
         return new ResponseEntity<>(service.getAllWarehouses(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<WarehouseDTO>> getWarehouseInfoById(@PathVariable String id){
        return new ResponseEntity<>(service.getWarehouseById(id), HttpStatus.OK);
    }
}
