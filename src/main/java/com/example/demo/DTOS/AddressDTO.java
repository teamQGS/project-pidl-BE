package com.example.demo.DTOS;

import lombok.Data;

@Data
public class AddressDTO {
    private String id;
    private String street;
    UserDTO userDTO;
    StaffDTO staffDTO;
}
