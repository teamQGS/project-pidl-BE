package com.example.demo.DTOS;

import lombok.Data;

@Data
public class UserDTO {
    String id;
    String username;
    String firstName;
    String lastName;
    Boolean isAdult;
    String image;
}
