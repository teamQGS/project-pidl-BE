package com.example.demo.model.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String country;
    private String city;
    private String street;
    private String house;
    private String postcode;
    private String countrycode;

}