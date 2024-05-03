package com.example.demo.controllers;

import com.example.demo.DTOS.CartDTO;
import com.example.demo.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/{username}")
    public ResponseEntity<CartDTO> getUserCart(@PathVariable String username){
        return new ResponseEntity<>(cartService.getCartByUsername(username), HttpStatus.OK);
    }
}
