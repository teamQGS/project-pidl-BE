package com.example.demo.DTOS;

import com.example.demo.model.Entities.Enums.Role;
import lombok.*;
import org.bson.types.ObjectId;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    String id;
    String username;
    String password;
    String firstName;
    String lastName;
    String email;
    String address;
    int age;
    String image;
    String token;
    String phoneNumber;
    Role role;
    String illustration;

}
