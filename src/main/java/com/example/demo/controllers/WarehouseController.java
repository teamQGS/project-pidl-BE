package com.example.demo.controllers;

import com.example.demo.model.Entities.WarehouseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/warehouse")
public class WarehouseController {
    @GetMapping
    public ResponseEntity<List<WarehouseEntity>> getWarehouseInfo(){
         //TODO warehouse logic return is for not getting an error
    }
}
