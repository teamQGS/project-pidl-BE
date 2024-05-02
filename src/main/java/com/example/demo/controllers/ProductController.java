package com.example.demo.controllers;

import com.example.demo.DTOS.ProductDTO;
import com.example.demo.model.Entities.Enums.ProductsCategory;
import com.example.demo.model.Entities.ProductEntity;
import com.example.demo.services.ProductService;
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
    ProductService productService;
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProductDTO>> getProductById(@PathVariable String id){
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Optional<ProductDTO>> deleteProductById(@PathVariable String id){
        return new ResponseEntity<>(productService.deleteProductById(id), HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<Optional<ProductDTO>> addProduct(@RequestBody ProductDTO productDTO){
        return new ResponseEntity<>(Optional.ofNullable(productService.createProduct(productDTO)), HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ProductEntity> updateProduct(@PathVariable String id, @RequestBody ProductDTO productDTO){
        ProductEntity updatedProduct = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable ProductsCategory category){
        return new ResponseEntity<>(productService.findProductsByCategory(category), HttpStatus.OK);
    }
}
