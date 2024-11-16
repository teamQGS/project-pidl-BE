package com.example.demo.controllers;


import com.example.demo.dto.UserDTO;
import com.example.demo.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return new ResponseEntity<>(adminService.getAllUsers(), HttpStatus.OK);
    }

    @PutMapping("/changeRole/{username}")
    public ResponseEntity<UserDTO> changeRole(@RequestBody String role, @PathVariable String username){
        return new ResponseEntity<>(adminService.changeRole(role, username), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Optional<UserDTO>> deleteUserByUsername(@PathVariable Long id){
        return new ResponseEntity<>(adminService.deleteUserById(id), HttpStatus.OK);
    }
}
