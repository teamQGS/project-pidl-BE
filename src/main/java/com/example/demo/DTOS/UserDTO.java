package com.example.demo.DTOS;

import com.example.demo.model.Entities.Enums.Role;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    long id;
    String username;
    String password;
    String firstName;
    String lastName;
    String email;
//    String image;
    String token;
    String phoneNumber;
    Role role;

}
