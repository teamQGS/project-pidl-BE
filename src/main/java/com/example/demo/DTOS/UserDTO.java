package com.example.demo.DTOS;

import lombok.*;
import org.bson.types.ObjectId;

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
    private int age;
    private String image;
    private String token;
}
