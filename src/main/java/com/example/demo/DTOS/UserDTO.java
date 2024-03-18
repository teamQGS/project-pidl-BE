package com.example.demo.DTOS;

import com.example.demo.model.Entities.AddressEntity;
import com.example.demo.model.Entities.ContactEntity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class UserDTO {
    Long id;
    String username;
    String firstName;
    String lastName;
    Boolean isAdult;
    String image;
    ContactDTO contact;
    AddressDTO address;
}
