package com.example.demo.controllers;

import com.example.demo.DTOS.*;
import com.example.demo.security.config.UserAuthProvider;
import com.example.demo.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    private UserAuthProvider userAuthProvider;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<UserDTO>> getUserById(@PathVariable ObjectId id){
        return new ResponseEntity<>(service.getUserById(id), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginDTO loginDTO){
        UserDTO user = service.login(loginDTO);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> register(@RequestBody SignUpDTO singUpDTO){
        UserDTO user = service.register(singUpDTO);
        return ResponseEntity.created(URI.create("/users/" + user.getId())).body(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<UserDTO> logout(@RequestHeader(value = "Authorization", required = false) String authentication) {
        if (authentication != null) {
            String[] authArray = authentication.split(" ");
            Authentication auth = userAuthProvider.validateToken(authArray[1]);
            UserDTO userDTO = (UserDTO) auth.getPrincipal();
            UserDTO dto = service.logout(userDTO);
            if (dto != null) {
                return ResponseEntity.ok(dto);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            System.out.println("NO HEADER AUTH"); //
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<Optional<UserDTO>> deleteUserByUsername(@PathVariable String username){
        return new ResponseEntity<>(service.deleteUserByUsername(username), HttpStatus.OK);
    }

    @PutMapping("/update/{username}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UpdateUserDTO updateUserDTO){
        return new ResponseEntity<>(service.updateUser(updateUserDTO), HttpStatus.OK);
    }
}

