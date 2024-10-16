package com.example.demo.DTOS;

import lombok.Data;

@Data
public class AddressDTO {
     private long id;
     private String username;
     private String country;
     private String city;
     private String street;
     private String house;
     private String postcode;
     private String countrycode;
}
