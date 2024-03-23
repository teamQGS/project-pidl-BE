package com.example.demo.model.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Contacts")
public class ContactEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private String phoneNumber;

    //It isn't good idea (get information about User or Staff) for SQL, but for MongoDB it's fine.
    //get id from User
//    @OneToMany(mappedBy = "contact")
//    private Set<UserEntity> users;
//
//    //get id from Staff
//    @OneToMany(mappedBy = "contact")
//    private Set<StaffEntity> staff;
}