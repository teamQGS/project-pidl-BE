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

    @PostMapping // Register user
    public String registerUser(@RequestBody UserDTO userDTO) {
        logger.info("Received request to register user {}", userDTO);
        String info = userService.createUser(userDTO);
        logger.info("User was created with ID: {}", info);
        return info;
    }

    // Testing access by email, if it's efficient
    @DeleteMapping("/delete/email/{email}") // Delete user by email
    public UserDTO deleteUserByEmail(@PathVariable String email) {
        logger.info("Received request to delete user with Email: {}", email);
        UserDTO userDTO = userService.deleteUserByEmail(email);
        logger.info("User with Email: {} was deleted", email);
        return userDTO;
    }

    // Old method to delete user by id, kept for reference
    // @DeleteMapping("/delete/{id}") // Delete user by id
    // public UserDTO deleteUser(@PathVariable String id) {
    //     logger.info("Received request to delete user with ID: {}", id);
    //     UserDTO userDTO = userService.deleteUser(id);
    //     logger.info("User with ID: {} was deleted", id);
    //     return userDTO;
    // }

    @GetMapping("/email/{email}") // Get user by email
    public UserDTO getUserByEmail(@PathVariable String email) {
        logger.info("Received request to get user with Email: {}", email);
        UserDTO userDTO = userService.getUserByEmail(email);
        logger.info("Returning user: {}", userDTO);
        return userDTO;
    }

    // Old method to get user by id, kept for reference
    // @GetMapping("/{id}") // Get user by id
    // public UserDTO getUser(@PathVariable String id) {
    //     logger.info("Received request to get user with ID: {}", id);
    //     UserDTO userDTO = userService.getUser(id);
    //     logger.info("Returning user: {}", userDTO);
    //     return userDTO;
    // }

    @PutMapping("/email/{email}") // Update user by email
    public UserDTO updateUserByEmail(@PathVariable String email, @RequestBody UserDTO userDTO) {
        logger.info("Received request to update user with Email: {}", email);
        UserDTO updatedUserDTO = userService.updateUserByEmail(email, userDTO);
        logger.info("User with Email: {} was updated", email);
        return updatedUserDTO;
    }

    // Old method to update user by id, kept for reference
    // @PutMapping("/{id}") // Update user
    // public UserDTO updateUser(@PathVariable String id, @RequestBody UserDTO userDTO) {
    //     logger.info("Received request to update user with ID: {}", id);
    //     UserDTO updatedUserDTO = userService.updateUser(id, userDTO);
    //     logger.info("User with ID: {} was updated", id);
    //     return updatedUserDTO;
    // }

    @GetMapping // Get all users
    public List<UserDTO> getAllUsers() {
        logger.info("Received request to get all users");
        List<UserDTO> users = userService.getAllUsers();
        logger.info("Returning {} users", users.size());
        return users;
    }
}