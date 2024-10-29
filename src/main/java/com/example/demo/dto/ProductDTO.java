package com.example.demo.dto;

import com.example.demo.model.entities.enums.ProductsCategory;
import lombok.Data;


@Data
public class ProductDTO {
     long id;
     String name;
     String description;
     double price;
     String illustration;
     int count;

     ProductsCategory productCategory;
//     Binary illustration;
}