package com.example.demo.controllers;

import com.example.demo.dto.*;
import com.example.demo.dto.records.LoginDTO;
import com.example.demo.dto.records.SignUpDTO;
import com.example.demo.dto.records.UpdatePasswordDTO;
import com.example.demo.dto.records.UpdateUserDTO;
import com.example.demo.security.config.AppException;
import com.example.demo.security.config.UserAuthProvider;
import com.example.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserAuthProvider userAuthProvider;

    @GetMapping("/{id}")
    public ResponseEntity<Optional<UserDTO>> getUserById(@PathVariable long id){
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/find/{username}")
    public ResponseEntity<Optional<UserDTO>> getUserByUsername(@PathVariable String username){
        Optional<UserDTO> user = userService.getUserByUsername(username);
        if (user.isPresent()) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginDTO loginDTO){
        UserDTO user = userService.login(loginDTO);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> register(@RequestBody SignUpDTO singUpDTO){
        UserDTO user = userService.register(singUpDTO);
        return ResponseEntity.created(URI.create("/users/" + user.getId())).body(user);
    }
    @PostMapping("/logout")
    public ResponseEntity<UserDTO> logout(@RequestHeader(value = "Authorization", required = false) String authentication) {
        if (authentication != null) {
            String[] authArray = authentication.split(" ");
            Authentication auth = userAuthProvider.validateToken(authArray[1]);
            UserDTO userDTO = (UserDTO) auth.getPrincipal();
            UserDTO dto = userService.logout(userDTO);
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
        return new ResponseEntity<>(userService.deleteUserByUsername(username), HttpStatus.OK);
    }

    @PutMapping("/update/{username}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UpdateUserDTO updateUserDTO, @PathVariable String username){
        return new ResponseEntity<>(userService.updateUser(updateUserDTO, username), HttpStatus.OK);
    }

    @PutMapping("/changePassword/{username}")
    public ResponseEntity<UserDTO> changePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO, @PathVariable String username){
        return new ResponseEntity<>(userService.changePassword(updatePasswordDTO, username), HttpStatus.OK);
    }
    @ExceptionHandler(AppException.class)
    public ResponseEntity<String> handleAppException(AppException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }


}

