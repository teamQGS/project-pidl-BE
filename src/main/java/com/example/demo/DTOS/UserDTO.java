package com.example.demo.DTOS;

import lombok.Data;

@Data
public class UserDTO {

    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private Boolean isAdult;
    private String image;
}
