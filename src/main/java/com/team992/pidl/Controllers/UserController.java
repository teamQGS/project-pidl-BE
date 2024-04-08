package com.team992.pidl.Controllers;

import com.team992.pidl.DTOS.UserDTO;
import com.team992.pidl.Service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/users")
@AllArgsConstructor // for userService
public class UserController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public String registerUser(@RequestBody UserDTO userDTO) {
        logger.info("Received request to register user {}", userDTO);
        String info = userService.createUser(userDTO);
        logger.info("User was created with ID: {}", info);
        return info;
    }

    @DeleteMapping("/delete/{id}")
    public UserDTO deleteUser(@PathVariable String id) {
        logger.info("Received request to delete user with ID: {}", id);
        UserDTO userDTO = userService.deleteUser(id);
        logger.info("User with ID: {} was deleted", id);
        return userDTO;
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable String id) {
        logger.info("Received request to get user with ID: {}", id);
        UserDTO userDTO = userService.getUser(id);
        logger.info("Returning user: {}", userDTO);
        return userDTO;
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable String id, @RequestBody UserDTO userDTO) {
        logger.info("Received request to update user with ID: {}", id);
        UserDTO updatedUserDTO = userService.updateUser(id, userDTO);
        logger.info("User with ID: {} was updated", id);
        return updatedUserDTO;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        logger.info("Received request to get all users");
        List<UserDTO> users = userService.getAllUsers();
        logger.info("Returning {} users", users.size());
        return users;
    }
}