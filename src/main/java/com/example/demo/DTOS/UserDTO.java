package com.example.demo.DTOS;

import com.example.demo.security.persistence.RoleEntity;
import lombok.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    String _id;
    String username;
    String password;
    String firstName;
    String lastName;
    int age;
    String image;
    String token;
    String phoneNumber;
    Set<RoleEntity> roles;

}
