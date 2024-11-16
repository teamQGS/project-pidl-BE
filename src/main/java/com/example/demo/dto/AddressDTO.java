package com.example.demo.dto;

import lombok.Data;

@Data
public class AddressDTO {
      Long id;
      String username;
      String country;
      String city;
      String street;
      String house;
      String postcode;
      String countrycode;
}
