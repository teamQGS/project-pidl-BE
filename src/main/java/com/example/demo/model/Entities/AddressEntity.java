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
@Table(name = "Addresses")
public class AddressEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String street;


    //It isn't good idea (get information about User or Staff) for SQL, but for mongoDB is fine.
    //get id from User
    @OneToMany(mappedBy = "address")
    private Set<UserEntity> users;

    //get id from Staff
    @OneToMany(mappedBy = "address")
    private Set<StaffEntity> staff;
}