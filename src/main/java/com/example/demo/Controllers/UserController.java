package com.example.demo.Controllers;

import com.example.demo.DTOS.LoginDTO;
import com.example.demo.DTOS.SignUpDTO;
import com.example.demo.DTOS.UserDTO;
import com.example.demo.Service.UserService;
import com.example.demo.config.UserAuthProvider;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;

    @Autowired
    private final UserAuthProvider userAuthProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginDTO loginDTO){
        UserDTO user = userService.login(loginDTO);
        user.setToken(userAuthProvider.createToken(user));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> register(@RequestBody SignUpDTO singUpDTO){
        UserDTO user = userService.register(singUpDTO);
        return ResponseEntity.created(URI.create("/users/" + user.getId())).body(user);
    }

//    @DeleteMapping("/delete/{id}")
//    public UserDTO deleteUser(@PathVariable Long id) {
//        return userService.deleteUser(id);
//    }
}
