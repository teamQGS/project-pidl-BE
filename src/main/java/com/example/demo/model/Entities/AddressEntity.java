package com.example.demo.model.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "address")
public class AddressEntity {
    @Id
    private String id;
    private String username;
    private String country;
    private String city;
    private String street;
    private String house;
    private String postcode;
    private String countrycode;
}