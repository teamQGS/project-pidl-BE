package com.example.demo.Controllers;

import com.example.demo.DTOS.UserDTO;
import com.example.demo.Service.UserService;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/users")
@AllArgsConstructor //for userService
public class UserController {
    @Autowired
    private final UserService userService;

    @PostMapping
    public Long registerUser(@RequestBody UserDTO userDTO) {
        System.out.println("Registering user " + userDTO);
        Long info = userService.createUser(userDTO);
        System.out.println("User was created with ID: " + info);
        return info;
    }

    @DeleteMapping("/delete/{id}")
    public UserDTO deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}
