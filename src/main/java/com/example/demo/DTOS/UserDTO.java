package com.example.demo.DTOS;

import lombok.Data;

@Data
public class UserDTO {

    private int _numericalId ;
    private String username;
    private String firstName;
    private String lastName;
    private int age;
    private Boolean isAdult;
    private String image;
}
