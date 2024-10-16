package com.example.demo.dto.records;


public record UpdateUserDTO (
    String firstName,
    String lastName,
    String email,
    String phoneNumber){}
