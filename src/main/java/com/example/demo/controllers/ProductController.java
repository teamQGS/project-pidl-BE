package com.example.demo.controllers;

import com.example.demo.DTOS.ProductDTO;
import com.example.demo.model.Entities.ProductEntity;
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
    public ResponseEntity<Optional<ProductDTO>> getProductById(@PathVariable String id){
        return new ResponseEntity<>(service.getProductById(id), HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Optional<ProductDTO>> deleteProductById(@PathVariable String id){
        return new ResponseEntity<>(service.deleteProductById(id), HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<Optional<ProductDTO>> addProduct(@RequestBody ProductDTO productDTO){
        return new ResponseEntity<>(Optional.ofNullable(service.createProduct(productDTO)), HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ProductEntity> updateProduct(@PathVariable String id, @RequestBody ProductDTO productDTO){
        ProductEntity updatedProduct = service.updateProduct(id, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }
}
