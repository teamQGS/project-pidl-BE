package com.example.demo.DTOS;

import com.example.demo.model.Entities.Enums.Role;
import lombok.Data;

@Data
public class StaffDTO {
    Long id;
    String firstName;
    String lastName;
    Role role;
    Boolean isAviable;
    String image;
    ContactDTO contact;
    AddressDTO address;
}
