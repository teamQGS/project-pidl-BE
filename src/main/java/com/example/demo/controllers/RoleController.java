package com.example.demo.controllers;

import com.example.demo.model.Entities.Enums.Role;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private UserService userService;

    @PostMapping("/assign/{username}")
    public ResponseEntity<?> assignRole(@PathVariable String username, @RequestBody Role role) {
        if (userService.userHasRole(username, Role.ADMIN)) {
            userService.assignRole(username, role);
            return ResponseEntity.ok("Role assigned successfully");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admins can assign roles");
        }
    }
}