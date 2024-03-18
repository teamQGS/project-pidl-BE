package com.example.demo.model.Entities;

import com.example.demo.model.Entities.Enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Staff")
public class StaffEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String firstName;

    private String lastName;

    private Role role;

    private Boolean isAviable;

    private String image; //Now it is a link to image. TODO create image variable

    //get id from Contact
    @ManyToOne
    private ContactEntity contact;
    @ManyToOne
    private AddressEntity address;
}