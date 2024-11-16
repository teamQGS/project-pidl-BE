package com.example.demo.dto;

import com.example.demo.model.entities.enums.Role;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    Long id;
    String username;
    String password;
    String firstName;
    String lastName;
    String email;
//    String image;
    String token;
    String phoneNumber;
    String role;

}
