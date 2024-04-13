package com.example.demo.DTOS;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class ContactDTO {
    private ObjectId id;
    private UserDTO userId;
    private String email;
    private String phone;
}
