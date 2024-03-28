package com.example.demo.controllers;

import com.example.demo.DTOS.ProductDTO;
import com.example.demo.services.ProductService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    ProductService service;
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProductDTO>> getProductById(@PathVariable ObjectId id){
        return new ResponseEntity<>(service.getProductById(id), HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Optional<ProductDTO>> deleteProductById(@PathVariable ObjectId id){
        return new ResponseEntity<>(service.deleteProductById(id), HttpStatus.OK);
    }
//    @PostMapping("/add")
//    public ResponseEntity<Optional>
}
