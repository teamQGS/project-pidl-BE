package com.example.demo.DTOS;

import com.example.demo.security.persistence.RoleEntity;
import lombok.*;
import org.bson.types.ObjectId;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private ObjectId id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Set<RoleEntity> roles;
    private int age;
    private String image;
    private String token;
}
