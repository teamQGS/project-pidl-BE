package com.team992.pidl.Controllers;

import com.team992.pidl.DTOS.UserDTO;
import com.team992.pidl.Service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/users")
@AllArgsConstructor // for userService
public class UserController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public String registerUser(@RequestBody UserDTO userDTO) {
        logger.info("Registering user {}", userDTO);
        String info = userService.createUser(userDTO);
        logger.info("User was created with ID: {}", info);
        return info;
    }

    @DeleteMapping("/delete/{id}")
    public UserDTO deleteUser(@PathVariable String id) {
        return userService.deleteUser(id);
    }
}