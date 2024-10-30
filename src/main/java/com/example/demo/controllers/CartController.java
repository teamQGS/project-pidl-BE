package com.example.demo.controllers;

import com.example.demo.dto.CartDTO;
import com.example.demo.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping("/{username}")
    public ResponseEntity<CartDTO> getUserCart(@PathVariable String username){
        return new ResponseEntity<>(cartService.getCartByUsername(username), HttpStatus.OK);
    }

    @PutMapping("/{username}/add")
    public ResponseEntity<CartDTO> addToCart(@RequestBody long productId, @PathVariable String username) {
        CartDTO updatedCart = cartService.addToCart(productId, username);
        return ResponseEntity.ok(updatedCart);
    }

    @PutMapping("/{username}/remove")
    public ResponseEntity<CartDTO> removeFromCart(@RequestBody long productId, @PathVariable String username) {
        CartDTO updatedCart = cartService.removeFromCart(productId, username);
        return ResponseEntity.ok(updatedCart);
    }

    @PutMapping("/{username}/clear")
    public ResponseEntity<CartDTO> clearCart(@PathVariable String username) {
        CartDTO updatedCart = cartService.clearCart(username);
        return ResponseEntity.ok(updatedCart);
    }

    @PutMapping("/{username}/increase")
    public ResponseEntity<CartDTO> increaseQuantity(@RequestBody long productId, @PathVariable String username) {
        CartDTO updatedCart = cartService.increaseCount(productId, username);
        return ResponseEntity.ok(updatedCart);
    }

    @PutMapping("/{username}/decrease")
    public ResponseEntity<CartDTO> decreaseQuantity(@RequestBody long productId, @PathVariable String username) {
        CartDTO updatedCart = cartService.decreaseCount(productId, username);
        return ResponseEntity.ok(updatedCart);
    }
}
