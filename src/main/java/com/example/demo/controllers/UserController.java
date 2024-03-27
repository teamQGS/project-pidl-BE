package com.example.demo.controllers;

import com.example.demo.model.Entities.UserEntity;
import com.example.demo.services.UserService;
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
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService service;
    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers(){
        return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<UserEntity>> getUserById(@PathVariable int id){
        return new ResponseEntity<>(service.getUserById(id), HttpStatus.OK);
    }
}
