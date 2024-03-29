package com.example.demo.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "https://pidl.onrender.com")
@RequestMapping("/api/warehouse")
public class WarehouseController {
//    @GetMapping
//    public ResponseEntity<List<WarehouseEntity>> getWarehouseInfo(){
//         //TODO warehouse logic return is for not getting an error
//    }
}
