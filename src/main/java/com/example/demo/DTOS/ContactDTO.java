package com.example.demo.DTOS;

import lombok.Data;

@Data
public class ContactDTO {
    private Long id;
    private String email;
    private String phoneNumber;
    UserDTO userDTO;
    StaffDTO staffDTO;
}
