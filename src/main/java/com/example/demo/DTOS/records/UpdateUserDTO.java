package com.example.demo.DTOS.records;

import lombok.Data;


public record UpdateUserDTO (
    String firstName,
    String lastName,
    String email,
    String phoneNumber){}
