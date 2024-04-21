package com.example.demo.DTOS;

import lombok.Data;


public record UpdateUserDTO (
    String firstName,
    String lastName,
    String email,
    String address,
    String phoneNumber){}
