package com.team992.pidl.DTOS;

import com.team992.pidl.Model.Entities.Enums.Role;

import lombok.Data;

@Data
public class StaffDTO {
    String id;
    String firstName;
    String lastName;
    Role role;
    Boolean isAviable;
    String image;
    String contactId;
    String addressId;
}