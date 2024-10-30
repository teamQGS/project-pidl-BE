package com.example.demo.dto;

import com.example.demo.model.entities.enums.ProductsCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;


@Data
public class ProductDTO {
     long id;
     String name;
     String description;
     double price;
     String illustration;
     int count;
     @Enumerated(EnumType.STRING)
     ProductsCategory productCategory;
//     Binary illustration;
}