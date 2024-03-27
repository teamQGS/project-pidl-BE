package com.example.demo.DTOS;

import com.example.demo.model.Entities.Enums.Role;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class StaffDTO {
    private ObjectId id;
    private String firstName;
    private String lastName;
    private Role role;
    private Boolean isAviable;
    private String image;
    ContactDTO contact;
    AddressDTO address;
}
