package com.example.demo.DTOS;

import com.example.demo.model.Entities.AddressEntity;
import com.example.demo.model.Entities.ContactEntity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    Long id;
    String username;
    String email;
    String token;
//    String firstName;
//    String lastName;
//    Boolean isAdult;
//    String image;
//    ContactDTO contact;
//    AddressDTO address;
}
