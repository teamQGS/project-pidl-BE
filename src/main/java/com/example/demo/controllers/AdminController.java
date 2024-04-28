package com.example.demo.controllers;


import com.example.demo.DTOS.UserDTO;
import com.example.demo.model.Entities.Enums.Role;
import com.example.demo.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return new ResponseEntity<>(adminService.getAllUsers(), HttpStatus.OK);
    }

    @PutMapping("/changeRole/{username}")
    public ResponseEntity<UserDTO> changeRole(@RequestBody String role, @PathVariable String username){
        return new ResponseEntity<>(adminService.changeRole(role, username), HttpStatus.OK);
    }
}
