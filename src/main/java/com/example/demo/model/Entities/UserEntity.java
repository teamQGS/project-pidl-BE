package com.example.demo.model.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
public class UserEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String email;

    private String password;

//    private String firstName;
//
//    private String lastName;
//
//    private Boolean isAdult;
//
//    private String image; //Now it is a link to image. TODO create image variable
//    //get id from Contact
//    @ManyToOne
//    private ContactEntity contact;
//    @ManyToOne
//    private AddressEntity address;
}